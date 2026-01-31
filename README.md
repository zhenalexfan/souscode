

![Souscode](web/public/og-image.jpg)

# Souscode: Recipe as Code

I spent the past few years as a software engineer, but was frustrated in my home kitchen. Traditional recipes are static, ambiguous, and hard to execute and scale. I started a weekend experiment: a strictly-typed Kotlin DSL for cooking.

The experiment became **Souscode**. It treats cooking like software engineering—providing precision, scalability, and reusability through code.

## Why Recipe as Code?

- **Version Controlled**: Treat your recipes like a living repository. Every tweak to a spice ratio or sourdough hydration is a commit. This allows for diffing different versions of a dish and rolling back to a previous "known good" state of a recipe.
- **Strictly-Typed Ingredients**: In this DSL, ingredients are not mere strings; they are objects with defined physical properties and metadata. This removes the ambiguity of "medium" sizes or "pinches."
- **Programmable & Executable**: By executing the recipe code, the system computes the exact required ingredients, transformations, and terminal states—transforming a descriptive document into a structured, verifiable plan for execution.
- **Composable Culinary Modules**: Don't reinvent the wheel—or the sauce. Standardized culinary techniques, such as a [mirepoix](https://en.wikipedia.org/wiki/Mirepoix) or a specific [emulsion](https://en.wikipedia.org/wiki/Emulsion), can be written as standalone modules and then imported and reused across different recipes.

## Sous OS & Recipe "Apps"

Kitchen seems like a great computing platform. Souscode could be the kernel of this environment:

*   **Sous OS**: A hardware-agnostic execution environment (the "Runtime") that can be embedded in your smart kitchen, translating pure logic into physical results.
*   **Recipes**: These are the "Apps." They contain the pure logic and state-checks (e.g., `water.boil()`) that the OS translates into specific instructions for your unique hardware.


## Getting Started

If you have just cloned the repository, follow these steps to get the app running:

### 1. Prerequisites
- **Java 17** (for building Kotlin Core/JVM tools)
- **Node.js** (for the Svelte frontend)

### 2. Install Dependencies
Navigate to the `web` directory and install the npm packages:
```bash
cd web
npm install
```

### 3. Generate Recipe Data
The frontend consumes JSON data generated directly from the Kotlin recipes. Run this from the **project root**:
```bash
./gradlew generateWebData
```
This will generate `web/src/data/recipes_en-US.json` and `web/src/data/recipes_zh-CN.json`. These files contain recipe metadata and a simplified list of instruction steps.

### 4. Build & Sync Kotlin Core (Optional)
The Kotlin Core library (JS build) is pre-synced in `web/src/lib/kotlin`. If you modify any Kotlin code in `src/kotlin/`, you must rebuild and sync it:
```bash
./gradlew assemble
cp build/dist/js/productionLibrary/*.mjs web/src/lib/kotlin/
```

### 5. Run Development Server
```bash
cd web
npm run dev
```

## Architecture

- **Svelte 5**: Utilizing the latest Runes API for reactive state management.
- **Kotlin**: The core cooking engine logic is written in Kotlin and compiled to JavaScript (ESM) for the browser.

## Join the Ecosystem

As an open-source experiment project, the license supports non-commercial use:

*   **For Developers**: I'm open-sourcing the DSL and the runtime. Let's "type-cast" the world's ingredients together and build the "recipe as code" database.
*   **For Hardware & Industry Partners**: The license is currently not for commercial use. If you are interested in this project, feel free to reach out.

Perhaps we could make our kitchen a better execution environment.
