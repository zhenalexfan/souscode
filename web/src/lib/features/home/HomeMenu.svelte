<script lang="ts">
  import { Ellipsis, ExternalLink, Info } from "lucide-svelte";
  import Menu from "../../shared/components/Menu.svelte";
  import MenuItem from "../../shared/components/MenuItem.svelte";
  import { cn } from "../../shared/cn";
  import { t } from "../../shared/i18n/i18n";
  import { AppUrls } from "../../shared/urls";

  interface Props {
    class?: string;
  }

  let { class: className = "" }: Props = $props();
</script>

<Menu class={className} align="right" triggerClass="rounded-full">
  {#snippet trigger({ open }: { open: boolean })}
    <button
      class={cn(
        "flex items-center px-4 py-3 rounded-full",
        "bg-surface hover:bg-surface-variant text-on-surface transition-colors duration-300",
        open && "bg-surface-variant",
      )}
    >
      <Ellipsis size={20} strokeWidth={2.5} />
    </button>
  {/snippet}

  {#snippet children()}
    <div class="flex flex-col p-2 min-w-48">
      <MenuItem
        icon={Info}
        onclick={() => {
          history.pushState(null, "", AppUrls.about());
          window.dispatchEvent(new Event("popstate"));
        }}
        class="px-4"
      >
        {$t("home.actionAbout")}
      </MenuItem>
      <MenuItem
        icon={ExternalLink}
        onclick={() => window.open(AppUrls.github(), "_blank")}
        class="px-4"
      >
        {$t("home.actionGithub")}
      </MenuItem>
    </div>
  {/snippet}
</Menu>
