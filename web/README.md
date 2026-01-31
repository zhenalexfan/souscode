# souscode.web

The modern recipe viewer for Souscode.

## 🚀 Getting Started

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
This will generate `src/data/recipes_en-US.json` and `src/data/recipes_zh-CN.json` in the `web` directory. These files contain recipe metadata and a simplified list of instruction steps.

### 4. Build & Sync Kotlin Core (Optional)
The Kotlin Core library (JS build) is pre-synced in `src/lib/kotlin`. If you modify any Kotlin code in `src/kotlin/`, you must rebuild and sync it:
```bash
./gradlew assemble
cp build/dist/js/productionLibrary/*.mjs web/src/lib/kotlin/
```

### 5. Run Development Server
```bash
cd web
npm run dev
```
Open [http://localhost:5173](http://localhost:5173) to view the app.

## 🐘 Gradle Tasks

These commands should be run from the **project root**:

- `./gradlew generateWebData`: Generates the recipe JSON data used by the frontend.
- `./gradlew assemble`: Compiles both JVM and JS targets.
- `./gradlew ktfmtFormatMain`: Automatically formats all Kotlin source code.
- `./gradlew ktfmtCheckMain`: Verifies that Kotlin source code follows the style guide.
- `./gradlew build`: Runs all checks and builds all artifacts.

## 🏛️ Architecture

- **Svelte 5**: Utilizing the latest Runes API for reactive state management.
- **Kotlin Multiplatform**: The core cooking engine logic is written in Kotlin and compiled to JavaScript (ESM) for the browser.
- **Dynamic Execution**: Unlike traditional apps that consume static JSON, the frontend **executes** the compiled Kotlin recipe logic in real-time to generate detailed state snapshots (temperature, ingredients, container states).
- **Tailwind CSS**: Modern styling with a custom theme.
- **Recipe DSL**: Recipes are defined in Kotlin, allowing for complex, logic-driven instruction generation.

## 📁 Project Structure

- `src/lib/kotlin/`: The compiled Kotlin Core library and its TypeScript wrapper.
- `src/lib/features/`: Component logic for recipes, players, and instruction lists.
- `src/data/`: Generated recipe JSON data.
- `src/app.css`: Custom Tailwind v4 configuration and theme.
