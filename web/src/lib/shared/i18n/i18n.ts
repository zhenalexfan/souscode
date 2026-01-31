import { derived, writable } from "svelte/store";
import enUS from "./locales/en-US.json";
import zhCN from "./locales/zh-CN.json";

export type Locale = "en-US" | "zh-CN";

const translations = {
  "en-US": enUS,
  "zh-CN": zhCN,
};

function getInitialLocale(): Locale {
  if (typeof window === "undefined") return "en-US";

  const saved = localStorage.getItem("locale") as Locale;
  if (saved && translations[saved]) return saved;

  const browserLocale = navigator.language;
  if (browserLocale.startsWith("zh")) return "zh-CN";

  return "en-US";
}

export const locale = writable<Locale>(getInitialLocale());

// Persist locale change
if (typeof window !== "undefined") {
  locale.subscribe((val) => {
    localStorage.setItem("locale", val);
    document.documentElement.lang = val;
  });
}

export const t = derived(locale, ($locale) => {
  return (key: string, params?: Record<string, string | number>) => {
    const keys = key.split(".");
    let result: any = translations[$locale];

    for (const k of keys) {
      if (result && result[k]) {
        result = result[k];
      } else {
        console.warn(
          `Translation key not found: ${key} for locale: ${$locale}`,
        );
        return key;
      }
    }

    if (params && typeof result === "string") {
      Object.entries(params).forEach(([k, v]) => {
        result = result.replace(new RegExp(`\\{${k}\\}`, "g"), String(v));
      });
    }

    return result;
  };
});
