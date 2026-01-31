<script lang="ts">
  import { locale } from "../../shared/i18n/i18n";
  import AboutEn, { metadata as metaEn } from "./content/About.en-US.md";
  import AboutZh, { metadata as metaZh } from "./content/About.zh-CN.md";
  import Article from "../common/Article.svelte";

  const contents = {
    "en-US": { component: AboutEn, metadata: metaEn },
    "zh-CN": { component: AboutZh, metadata: metaZh },
  };

  const activeData = $derived(
    contents[$locale as keyof typeof contents] ?? contents["en-US"],
  );

  let ActiveComponent = $derived(activeData.component);
</script>

<Article
  title={activeData.metadata.title}
  copyright={activeData.metadata.copyright}
>
  <ActiveComponent />
</Article>
