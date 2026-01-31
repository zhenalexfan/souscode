#!/bin/bash
set -e

# 1. Install Java 17 (Required for Gradle)
echo "📦 Installing Java 17..."
curl -sL https://corretto.aws/downloads/latest/amazon-corretto-17-x64-linux-jdk.tar.gz -o jdk.tar.gz
mkdir -p .jdk
tar -xzf jdk.tar.gz -C .jdk --strip-components=1
export JAVA_HOME="$(pwd)/.jdk"
export PATH="$JAVA_HOME/bin:$PATH"
java -version

# 2. Build Kotlin Artifacts (JSON + JS)
echo "🐘 Building Kotlin Core..."
chmod +x ./gradlew
./gradlew generateWebData assemble --no-daemon

# 3. Sync Artifacts to Web Directory
echo "🔄 Syncing artifacts to web/src/lib/kotlin..."
mkdir -p web/src/lib/kotlin
cp build/dist/js/productionLibrary/*.mjs web/src/lib/kotlin/

# 4. Build Vite App
echo "⚡ Building Vite App..."
cd web
npm install
npm run build
