<script lang="ts">
  import { locale, type Locale, t } from "../i18n/i18n";
  import { Languages, ChevronDown, Check } from "lucide-svelte";
  import { cn } from "../cn";
  import Menu from "./Menu.svelte";
  import MenuItem from "./MenuItem.svelte";

  const options: { labelKey: string; value: Locale }[] = [
    { labelKey: "language.enUS", value: "en-US" },
    { labelKey: "language.zhCN", value: "zh-CN" },
  ];

  function select(value: Locale) {
    $locale = value;
  }
</script>

<Menu align="right" triggerClass="rounded-full">
  {#snippet trigger({ open }: { open: boolean })}
    <button
      class={cn(
        "flex items-center px-4 py-3 rounded-full",
        "bg-surface hover:bg-surface-variant text-on-surface transition-colors duration-300",
        open && "bg-surface-variant",
      )}
    >
      <Languages size={20} strokeWidth={2.5} />
      <ChevronDown
        size={16}
        class="transition-transform duration-300 {open ? 'rotate-180' : ''}"
      />
    </button>
  {/snippet}

  {#snippet children()}
    <div class="flex flex-col p-2 min-w-48">
      {#each options as option}
        <MenuItem onclick={() => select(option.value)} class="px-4">
          <div class="flex items-center justify-between w-full">
            <span
              class={cn(
                "font-medium",
                option.value === $locale ? "text-primary" : "text-on-surface",
              )}
            >
              {$t(option.labelKey)}
            </span>
            {#if option.value === $locale}
              <Check size={16} class="text-primary" />
            {/if}
          </div>
        </MenuItem>
      {/each}
    </div>
  {/snippet}
</Menu>
