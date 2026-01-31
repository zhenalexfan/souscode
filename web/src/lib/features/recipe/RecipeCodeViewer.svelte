<script lang="ts">
  import { ArrowLeft, Code } from "lucide-svelte";
  import CodeViewer from "../../shared/components/CodeViewer.svelte";
  import BackButton from "../common/BackButton.svelte";
  import { t } from "../../shared/i18n/i18n";

  let { code }: { code: string } = $props();

  // Logic to calculate collapsible ranges
  const collapsibleRanges = $derived.by(() => {
    const lines = code.split("\n");
    const ranges: { start: number; end: number; label?: string }[] = [];

    // 1. Package and Imports
    let packageAndImportStart = -1;
    let packageAndImportEnd = -1;

    for (let i = 0; i < lines.length; i++) {
      const line = lines[i].trim();
      if (line.startsWith("package ") || line.startsWith("import ")) {
        if (packageAndImportStart === -1) packageAndImportStart = i + 1;
        packageAndImportEnd = i + 1;
      } else if (line === "" && packageAndImportStart !== -1) {
        // Keep whitespace with the block if it's contiguous
        packageAndImportEnd = i + 1;
      } else if (line !== "" && packageAndImportStart !== -1) {
        break;
      }
    }

    if (packageAndImportStart !== -1) {
      ranges.push({
        start: packageAndImportStart,
        end: packageAndImportEnd,
        label: $t("recipe.code.packageImports"),
      });
    }

    // 2. Recipe Name Initialization
    const nameRegex = /override\s+val\s+name\s*=/;
    for (let i = 0; i < lines.length; i++) {
      if (nameRegex.test(lines[i])) {
        ranges.push({
          start: i + 1,
          end: i + 1,
          label: $t("recipe.code.recipeName"),
        });
      }
    }

    return ranges;
  });
</script>

<div
  class="flex-1 bg-code-surface rounded-[2.5rem] shadow-2xl relative group overflow-hidden flex flex-col min-h-0"
>
  <BackButton href="#/" class="absolute top-8 left-8 z-20" />
  <div
    class="absolute top-8 right-8 text-[10px] font-bold text-on-code-surface/50 uppercase tracking-[0.1em] bg-white/5 px-4 py-2 rounded-full flex items-center gap-2 z-20"
  >
    <Code size={12} />
    Kotlin
  </div>

  <div
    class="flex-1 p-8 lg:p-12 overflow-auto text-on-code-surface font-mono text-sm custom-scrollbar"
  >
    <div class="mt-20">
      <CodeViewer {code} language="kotlin" {collapsibleRanges} class="-ml-4" />
    </div>
  </div>
</div>

<style>
  .custom-scrollbar::-webkit-scrollbar {
    width: 0px;
    background: transparent;
  }
  .custom-scrollbar {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  }
</style>
