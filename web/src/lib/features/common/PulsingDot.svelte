<script lang="ts">
  import { cn } from "../../shared/cn";

  interface Props {
    class?: string;
  }

  let { class: className }: Props = $props();
</script>

<div class={cn("relative flex items-center justify-center", className)}>
  <div
    class="w-2 h-2 rounded-full bg-primary animate-ping absolute inset-0"
  ></div>
  <div class="w-2 h-2 rounded-full bg-primary relative"></div>
</div>
