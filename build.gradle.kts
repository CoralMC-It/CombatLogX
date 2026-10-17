val baseVersion = providers.gradleProperty("version.base").get()
val betaVersion = providers.gradleProperty("version.beta").get().toBooleanStrict()
val betaString = if (betaVersion) "Beta-" else ""
val jenkinsBuild = providers.environmentVariable("BUILD_NUMBER").orElse("Unofficial").get()
rootProject.version = "${baseVersion}.${betaString}${jenkinsBuild}"

plugins {
    id("java")
}

subprojects {
    apply(plugin = "java")

    java {
        val selectedDevPaper = providers.gradleProperty("platform").orNull == "paper"
        sourceCompatibility = if (selectedDevPaper) JavaVersion.VERSION_25 else JavaVersion.VERSION_21
        targetCompatibility = if (selectedDevPaper) JavaVersion.VERSION_25 else JavaVersion.VERSION_21
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }

    repositories {
        mavenLocal()
        maven("https://repo.coralmc.it/releases") { // CoralMC repository (BlueSlimeCore fork)
            credentials {
                username = providers.gradleProperty("mavenUser").orNull
                password = providers.gradleProperty("mavenPassword").orNull
            }
        }
        maven("https://nexus.sirblobman.xyz/central-mirror/") // Central Mirror
        maven("https://repo.aikar.co/nexus/content/groups/aikar/") // Aikar Repository
        maven("https://nexus.sirblobman.xyz/public/") // SirBlobman Public Repository
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.1.0") // JetBrains Annotations
        compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT") // Base Spigot API
        compileOnly("com.github.sirblobman.api:core:2.9-coral-SNAPSHOT") // BlueSlimeCore (CoralMC fork)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:deprecation")
    }
}
