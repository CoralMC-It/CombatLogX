java {
    sourceCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    targetCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    maven("https://repo.william278.net/releases/")
}

dependencies {
    compileOnly("net.william278.husksync:husksync-bukkit:3.8.5+1.21.6")
}
