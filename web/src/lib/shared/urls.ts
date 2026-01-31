export function toKebabCase(str: string): string {
  return str
    .replace(/([a-z0-9])([A-Z])/g, "$1-$2")
    .replace(/([A-Z])([A-Z][a-z])/g, "$1-$2")
    .toLowerCase();
}

export function toPascalCase(str: string): string {
  return str
    .replace(/(^|-)([a-z])/g, (_match, _separator, char) => char.toUpperCase())
    .replace(/-/g, "");
}

export const AppUrls = {
  home: () => "/",
  about: () => "/about",
  recipe: (id: string) => `/recipe/${toKebabCase(id)}`,
  player: (id: string) => `/player/${toKebabCase(id)}`,
  github: () => "https://github.com/zhenalexfan/souscode",
};

export type RouteInfo =
  | { route: "home" }
  | { route: "about" }
  | { route: "recipe"; id: string }
  | { route: "player"; id: string };

export function resolveRoute(path: string): RouteInfo {
  // Remove trailing slash if present (except for root)
  const normalizedPath =
    path.length > 1 && path.endsWith("/") ? path.slice(0, -1) : path;

  if (normalizedPath === "/about") {
    return { route: "about" };
  }

  if (normalizedPath.startsWith("/recipe/")) {
    const slug = normalizedPath.split("/")[2];
    return { route: "recipe", id: toPascalCase(slug) };
  }

  if (normalizedPath.startsWith("/player/")) {
    const slug = normalizedPath.split("/")[2];
    return { route: "player", id: toPascalCase(slug) };
  }

  return { route: "home" };
}
