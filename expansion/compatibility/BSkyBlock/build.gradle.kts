java {
    sourceCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    targetCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    maven("https://repo.codemc.io/repository/bentoboxworld/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("world.bentobox:bentobox:3.3.5-SNAPSHOT") // BentoBox
    compileOnly("world.bentobox:bskyblock:1.19.1-SNAPSHOT") // BSkyBlock
}
