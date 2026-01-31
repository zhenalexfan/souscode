<script lang="ts">
  import { GitBranch, ShieldCheck, Cpu, Box } from "lucide-svelte";
  import { t } from "../../shared/i18n/i18n";
  import { cn } from "../../shared/cn";
  import AboutFeature from "./AboutFeature.svelte";
</script>

<div class="@container py-12">
  <div class={cn("grid grid-cols-1 @[400px]:grid-cols-2 gap-6")}>
    <AboutFeature
      title={$t("about.features.1.title")}
      description={$t("about.features.1.description")}
    >
      {#snippet icon()}
        <GitBranch size={24} strokeWidth={2.5} />
      {/snippet}
    </AboutFeature>

    <AboutFeature
      title={$t("about.features.2.title")}
      description={$t("about.features.2.description", {
        brand: "Souscode",
      })}
    >
      {#snippet icon()}
        <ShieldCheck size={24} strokeWidth={2.5} />
      {/snippet}
    </AboutFeature>

    <AboutFeature
      title={$t("about.features.3.title")}
      description={$t("about.features.3.description")}
    >
      {#snippet icon()}
        <Cpu size={24} strokeWidth={2.5} />
      {/snippet}
    </AboutFeature>

    <AboutFeature
      title={$t("about.features.4.title")}
      description={$t("about.features.4.description")}
    >
      {#snippet icon()}
        <Box size={24} strokeWidth={2.5} />
      {/snippet}
    </AboutFeature>
  </div>
</div>
