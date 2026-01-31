<script lang="ts">
  import type { Snippet } from "svelte";
  import { cn } from "../../shared/cn";

  let {
    class: className,
    children,
  }: {
    class?: string;
    children?: Snippet;
  } = $props();
</script>

<div class={cn("relative flex items-center justify-center", className)}>
  <svg
    viewBox="0 0 171 159"
    class="absolute inset-0 w-full h-full fill-current select-none pointer-events-none"
  >
    <path
      d="M164.656 56.5577C172.837 70.5738 172.837 87.9089 164.656 101.925L144.671 136.166C136.605 149.986 121.808 158.483 105.807 158.483L64.9853 158.483C48.9842 158.483 34.1866 149.986 26.1208 136.166L6.13547 101.925C-2.04516 87.9089 -2.04517 70.5738 6.13547 56.5577L26.1208 22.3163C34.1866 8.49689 48.9842 1.99448e-06 64.9853 2.69391e-06L105.807 4.47827e-06C121.808 5.1777e-06 136.605 8.49689 144.671 22.3163L164.656 56.5577Z"
    />
  </svg>
  <div class="relative z-10 flex items-center justify-center">
    {@render children?.()}
  </div>
</div>
