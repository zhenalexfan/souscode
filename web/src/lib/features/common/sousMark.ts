export interface Token {
  type: "text" | "ingredient" | "container";
  content: string;
  id?: string;
  kind?: string;
}

export function parseSousMark(text: string): Token[] {
  if (!text) return [];
  // Matches <tag attr="val">content</tag> or plain text
  const regex = /<(\w+)\s+([^>]*?)>(.*?)<\/\1>|([^<]+|<)/g;
  const tokens: Token[] = [];
  let match;

  while ((match = regex.exec(text)) !== null) {
    if (match[4]) {
      tokens.push({ type: "text", content: match[4] });
    } else {
      const type = match[1] as "ingredient" | "container";
      const attributes = match[2];
      const content = match[3];

      const idMatch = attributes.match(/id="([^"]*)"/);
      const id = idMatch ? idMatch[1] : undefined;

      let kind: string | undefined;
      if (type === "container") {
        if (attributes.includes("cookware")) kind = "cookware";
        else if (attributes.includes("tableware")) kind = "tableware";
      }

      tokens.push({ type, content, id, kind });
    }
  }
  return tokens;
}
