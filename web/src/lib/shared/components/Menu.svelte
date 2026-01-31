<script lang="ts">
  import { tick } from "svelte";
  import { cn } from "../cn";
  import Popover from "./Popover.svelte";

  interface Props {
    open?: boolean;
    onOpenChange?: (open: boolean) => void;
    align?: "left" | "right" | "center";
    side?: "top" | "bottom";
    class?: string;
    triggerClass?: string;
    panelClass?: string;
    trigger?: any; // Snippet that receives { open, id, ariaProps }
    children?: any; // Menu panel content snippet
  }

  let {
    open = $bindable(false),
    onOpenChange,
    align = "right",
    side = "bottom",
    class: className = "",
    triggerClass = "",
    panelClass = "",
    trigger,
    children,
  }: Props = $props();

  let triggerRef: HTMLElement | null = $state(null);
  let menuRef: HTMLDivElement | null = $state(null);
  let containerRef: HTMLDivElement | null = $state(null);

  const menuId = `menu-${Math.random().toString(36).substr(2, 9)}`;
  const triggerId = `${menuId}-trigger`;

  async function openMenu({ focusFirstItem = false } = {}) {
    open = true;
    onOpenChange?.(true);
    await tick();
    if (focusFirstItem) {
      focusMenuItem(0);
    }
  }

  function closeMenu({ focusTrigger = false } = {}) {
    if (!open) return;
    open = false;
    onOpenChange?.(false);
    if (focusTrigger) {
      triggerRef?.focus();
    }
  }

  function getMenuItems() {
    return menuRef
      ? Array.from(menuRef.querySelectorAll<HTMLElement>("[data-menu-item]"))
      : [];
  }

  function focusMenuItem(index: number) {
    const items = getMenuItems();
    if (!items.length) return;
    const normalizedIndex = (index + items.length) % items.length;
    items[normalizedIndex]?.focus();
  }

  async function handleTriggerClick(event: MouseEvent) {
    if (open) {
      closeMenu();
      return;
    }
    await openMenu({ focusFirstItem: event.detail === 0 });
  }

  async function handleTriggerKeydown(event: KeyboardEvent) {
    if (event.key === "ArrowDown") {
      event.preventDefault();
      if (!open) {
        await openMenu({ focusFirstItem: true });
      } else {
        focusMenuItem(0);
      }
      return;
    }

    if (event.key === "ArrowUp") {
      event.preventDefault();
      if (!open) {
        await openMenu({ focusFirstItem: true });
      }
      const items = getMenuItems();
      if (items.length) {
        focusMenuItem(items.length - 1);
      }
      return;
    }

    if (event.key === "Escape" && open) {
      event.preventDefault();
      closeMenu({ focusTrigger: true });
    }
  }

  function handleMenuKeydown(event: KeyboardEvent) {
    const items = getMenuItems();
    if (!items.length) return;

    const currentIndex = items.findIndex(
      (item) => item === document.activeElement,
    );

    if (event.key === "ArrowDown") {
      event.preventDefault();
      focusMenuItem(currentIndex + 1);
      return;
    }

    if (event.key === "ArrowUp") {
      event.preventDefault();
      focusMenuItem(currentIndex - 1);
      return;
    }

    if (event.key === "Home") {
      event.preventDefault();
      focusMenuItem(0);
      return;
    }

    if (event.key === "End") {
      event.preventDefault();
      focusMenuItem(items.length - 1);
      return;
    }

    if (event.key === "Escape") {
      event.preventDefault();
      closeMenu({ focusTrigger: true });
    }
  }

  function handleDocumentClick(event: MouseEvent) {
    if (!open || !containerRef) return;
    const target = event.target as Node;
    // When portaled, the menu is outside the container; keep it open when clicking inside the menu
    if (
      !containerRef.contains(target) &&
      !(menuRef && menuRef.contains(target))
    ) {
      closeMenu();
    }
  }

  function handleWindowKeydown(event: KeyboardEvent) {
    if (!open) return;
    if (event.key === "Escape") {
      event.preventDefault();
      closeMenu({ focusTrigger: true });
    }
  }
</script>

<svelte:window onclick={handleDocumentClick} onkeydown={handleWindowKeydown} />

<div class={cn("relative inline-flex", className)} bind:this={containerRef}>
  {#if trigger}
    <div
      bind:this={triggerRef}
      id={triggerId}
      role="button"
      tabindex="0"
      aria-haspopup="menu"
      aria-controls={menuId}
      aria-expanded={open}
      class={cn(
        "inline-flex items-center leading-none cursor-pointer focus-ring rounded-md",
        triggerClass,
      )}
      onclick={handleTriggerClick}
      onkeydown={handleTriggerKeydown}
    >
      {@render trigger({ open, id: triggerId, menuId })}
    </div>
  {/if}

  <Popover
    anchor={triggerRef}
    bind:element={menuRef}
    {open}
    {align}
    {side}
    id={menuId}
    role="menu"
    tabindex="-1"
    aria-labelledby={triggerId}
    class={cn(
      "bg-surface/90 text-on-surface brightness-100",
      "menu-panel fixed z-[10000] w-64 rounded-2xl p-1 pb-2 shadow-xl backdrop-blur",
      panelClass,
    )}
    onkeydown={handleMenuKeydown}
  >
    {@render children?.()}
  </Popover>
</div>

<style>
  @keyframes menuScaleIn {
    from {
      opacity: 0;
      transform: translateY(-4px) scale(0.98);
    }
    to {
      opacity: 1;
      transform: translateY(0) scale(1);
    }
  }

  :global(.menu-panel) {
    animation: menuScaleIn 0.16s ease-out;
  }
</style>
