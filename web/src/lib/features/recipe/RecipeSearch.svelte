<script lang="ts">
  import { Search } from "lucide-svelte";
  import { t } from "../../shared/i18n/i18n";

  let {
    searchQuery = $bindable(""),
    class: className,
  }: { searchQuery: string; class?: string } = $props();
</script>

<div class="relative max-w-3xl group {className}">
  <div
    class="absolute inset-y-0 left-0 pl-8 flex items-center pointer-events-none"
  >
    <Search
      class="h-10 w-10 text-gray-300 group-focus-within:text-on-surface transition-colors duration-300"
      strokeWidth={3}
    />
  </div>
  <input
    type="text"
    bind:value={searchQuery}
    placeholder={$t("search.placeholder")}
    class="headline block w-full pl-24 pr-8 py-8 rounded-[2.5rem] bg-surface text-on-surface text-4xl placeholder-gray-200 focus:outline-none shadow-none hover:shadow-xl focus:shadow-2xl transition-all duration-300"
  />
</div>
