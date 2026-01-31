export function toKebabCase(str: string): string {
  return str.replace(/([a-z0-9])([A-Z])/g, "$1-$2").toLowerCase();
}

export function toPascalCase(str: string): string {
  return str
    .replace(/(^|-)([a-z])/g, (_match, _separator, char) => char.toUpperCase())
    .replace(/-/g, "");
}
