export function portal(node: HTMLElement, target: HTMLElement | string = "body") {
  const targetEl: HTMLElement | null =
    typeof target === "string" ? (document.querySelector(target) as HTMLElement) : target;

  if (!targetEl) return;

  targetEl.appendChild(node);

  return {
    destroy() {
      if (node && targetEl && targetEl.contains(node)) {
        targetEl.removeChild(node);
      }
    },
  };
}
