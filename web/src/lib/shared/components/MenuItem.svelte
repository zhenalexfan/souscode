<script lang="ts">
  import { cn } from "../cn";
  import type { HTMLButtonAttributes } from "svelte/elements";

  interface Props extends HTMLButtonAttributes {
    icon?: any; // Component or snippet for icon
    variant?: "default" | "danger";
    disabled?: boolean;
    loading?: boolean;
    class?: string;
    onclick?: () => void;
    children?: any;
  }

  let {
    icon,
    variant = "default",
    disabled = false,
    loading = false,
    class: className = "",
    onclick,
    children,
    ...restProps
  }: Props = $props();

  const variantClasses = {
    default:
      "text-on-surface hover:bg-surface-variant focus-visible:ring-accent/30",
    danger:
      "text-red-500 hover:bg-red-50 hover:text-red-600 focus-visible:ring-red-200",
  };

  const isDisabled = disabled || loading;
</script>

<button
  type="button"
  role="menuitem"
  data-menu-item
  {disabled}
  {onclick}
  class={cn(
    "flex w-full items-center gap-3 rounded-xl px-3 py-2 text-left text-sm transition-colors duration-150 focus-visible:outline-none focus-visible:ring-2",
    variantClasses[variant],
    loading && "cursor-wait opacity-80",
    isDisabled && "opacity-50 cursor-not-allowed",
    className,
  )}
  {...restProps}
>
  {#if icon}
    <div class="h-4 w-4 flex-shrink-0 flex items-center justify-center">
      {#if typeof icon === "function"}
        {@render icon({ loading, variant })}
      {:else}
        {@const SvelteComponent = icon}
        <SvelteComponent
          class={cn(
            "h-4 w-4",
            variant === "danger" ? "text-red-500" : "text-gray-400",
            loading && "animate-spin",
          )}
          aria-hidden="true"
        />
      {/if}
    </div>
  {/if}
  <span
    class={cn(
      "font-medium w-full text-sm",
      variant === "danger" && "text-current",
    )}
  >
    {#if children}
      {@render children()}
    {/if}
  </span>
</button>
