import {
  Add,
  Done,
  Heat,
  Locale,
  Mix,
  RecipeRegistry,
  SnapshotLogger,
  Transfer,
  Wait,
} from "../../kotlin/souscode.mjs";
import { type PlayerAction, InstructionStepCategory, Intensity } from "./types";

export class RecipePlayerStore {
  // State
  id = $state("");
  kotlinRecipe = $state<any>(null);
  error = $state<string | null>(null);
  recipeName = $state("");
  flattenedActions = $state<PlayerAction[]>([]);
  currentLocale = $state<string>("en-US");

  // Playback State
  currentStepIndex = $state(0);
  isPlaying = $state(false);
  progress = $state(0);

  private timer: any = null;
  private readonly TICK_MS = 50;
  private readonly DEFAULT_STEP_DURATION_MS = 4000;

  constructor() {
    $effect(() => {
      if (this.isPlaying) {
        this.timer = setInterval(() => {
          const action = this.flattenedActions[this.currentStepIndex];
          if (!action) return;

          const durationMs = this.getStepDurationMs(action);
          this.progress += this.TICK_MS / durationMs;

          if (this.progress >= 1) {
            this.progress = 0;
            this.nextStep();
          }
        }, this.TICK_MS);
      } else {
        this.clearInterval();
      }
      return () => this.clearInterval();
    });

    $effect(() => {
      if (this.kotlinRecipe && this.currentLocale) {
        this.processRecipe();
      }
    });
  }

  private getKotlinLocale(): any {
    if (this.currentLocale === "zh-CN") {
      return (Locale as any).ZH_CN;
    }
    return (Locale as any).EN_US;
  }

  private getStepDurationMs(action: PlayerAction): number {
    if (
      action.instruction.category === InstructionStepCategory.Wait &&
      action.instruction.durationSeconds
    ) {
      return action.instruction.durationSeconds * 1000;
    }
    return this.DEFAULT_STEP_DURATION_MS;
  }

  load(recipeId: string) {
    this.id = recipeId;
    try {
      if (!(RecipeRegistry as any)) {
        throw new Error(
          "RecipeRegistry is undefined. The Kotlin library failed to load.",
        );
      }

      const registry = (RecipeRegistry as any).getInstance();
      const recipes = registry.getRecipes();

      let recipe = registry.getRecipeById(this.id);

      if (!recipe) {
        const targetId = this.id.toLowerCase();
        recipe = recipes.find((r: any) => {
          const constr = r.constructor?.name?.toLowerCase() || "";
          const str = r.toString().toLowerCase();
          return (
            constr === targetId ||
            constr.includes(targetId) ||
            str.includes(targetId)
          );
        });
      }

      if (!recipe) {
        throw new Error(`Recipe with ID "${this.id}" not found.`);
      }

      this.kotlinRecipe = recipe;
    } catch (e: any) {
      console.error(e);
      this.error = e.message;
    }
  }

  private processRecipe() {
    try {
      const kotlinLocale = this.getKotlinLocale();
      this.recipeName = this.kotlinRecipe.name.invoke(kotlinLocale);
      const logger = new SnapshotLogger(kotlinLocale);
      logger.record(this.kotlinRecipe);
      const steps = logger.getSteps();

      this.flattenedActions = steps.map((step: any): PlayerAction => {
        const instruction = step.instruction;
        const category = instruction.category;
        let categoryEnum = InstructionStepCategory.Add;

        if (category === Add.getInstance()) {
          categoryEnum = InstructionStepCategory.Add;
        } else if (category instanceof Heat) {
          categoryEnum = InstructionStepCategory.Heat;
        } else if (category === Mix.getInstance()) {
          categoryEnum = InstructionStepCategory.Mix;
        } else if (category instanceof Wait) {
          categoryEnum = InstructionStepCategory.Wait;
        } else if (category === Transfer.getInstance()) {
          categoryEnum = InstructionStepCategory.Transfer;
        } else if (category === Done.getInstance()) {
          categoryEnum = InstructionStepCategory.Done;
        }

        const intensityMap: Record<string, Intensity> = {
          Low: Intensity.Low,
          Medium: Intensity.Medium,
          High: Intensity.High,
        };

        return {
          instruction: {
            category: categoryEnum,
            description: instruction.description,
            intensity: category.intensity?.name
              ? intensityMap[category.intensity.name]
              : null,
            durationSeconds: category.duration?.inSeconds,
          },
          containerName: step.containerName,
          snapshots: step.snapshots.map((s: any) => ({
            id: s.id,
            name: s.name,
            state: s.state,
            temperature: s.temperature,
            contents: s.contents,
            isHeated: s.isHeated,
            intensity: s.intensity ? intensityMap[s.intensity] : null,
          })),
        };
      });
    } catch (e: any) {
      console.error("Error executing recipe:", e);
      this.error = "Execution Error: " + (e.message || String(e));
    }
  }

  nextStep() {
    this.progress = 0;
    if (this.currentStepIndex < this.flattenedActions.length - 1) {
      this.currentStepIndex++;
    } else {
      this.isPlaying = false;
    }
  }

  prevStep() {
    this.progress = 0;
    if (this.currentStepIndex > 0) {
      this.currentStepIndex--;
    }
  }

  togglePlay() {
    this.isPlaying = !this.isPlaying;
  }

  selectStep(index: number) {
    this.progress = 0;
    this.currentStepIndex = index;
  }

  private clearInterval() {
    if (this.timer) {
      clearInterval(this.timer);
      this.timer = null;
    }
  }

  destroy() {
    this.clearInterval();
  }
}
