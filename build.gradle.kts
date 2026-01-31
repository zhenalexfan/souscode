plugins {
  kotlin("multiplatform") version "1.9.24"
  kotlin("plugin.serialization") version "1.9.24"
  id("com.ncorti.ktfmt.gradle") version "0.25.0"
}

ktfmt {
  googleStyle()
  maxWidth = 80
}

repositories { mavenCentral() }

kotlin {
  jvm { jvmToolchain(17) }
  js(IR) {
    useEsModules()
    browser {
      binaries.library()
    }
  }

  sourceSets {
    all {
      languageSettings {
        optIn("kotlin.js.ExperimentalJsExport")
      }
    }
    val commonMain by getting {
      kotlin.srcDir("src/kotlin")
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
      }
    }
    val commonTest by getting {
      kotlin.srcDir("src/test/kotlin")
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val jvmMain by getting
    val jsMain by getting
  }
}

// Global ktfmt configuration in the ktfmt { ... } block is enough.

tasks.register<JavaExec>("generateWebData") {
  group = "application"
  description = "Generates web data from recipes"
  val jvmSourceSet = kotlin.targets.getByName("jvm").compilations.getByName("main").output.allOutputs
  classpath(jvmSourceSet, configurations.getByName("jvmRuntimeClasspath"))
  mainClass.set("souscode.tools.RecipeWebExporterKt")
  workingDir = rootDir
}

tasks.register<JavaExec>("debugSnapshotLogger") {
  group = "application"
  description = "Debugs SnapshotLogger"
  val jvmSourceSet =
    kotlin.targets.getByName("jvm").compilations.getByName("main").output.allOutputs
  classpath(jvmSourceSet, configurations.getByName("jvmRuntimeClasspath"))
  mainClass.set("souscode.tools.SnapshotDebuggerKt")
  workingDir = rootDir
}

tasks.register("ktfmtFormatMain") {
  group = "formatting"
  description = "Formats all main Kotlin sources"
  dependsOn("ktfmtFormatKmpCommonMain", "ktfmtFormatKmpJsMain", "ktfmtFormatKmpJvmMain")
}

tasks.register("ktfmtCheckMain") {
  group = "formatting"
  description = "Checks formatting of all main Kotlin sources"
  dependsOn("ktfmtCheckKmpCommonMain", "ktfmtCheckKmpJsMain", "ktfmtCheckKmpJvmMain")
}
