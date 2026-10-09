java {
    sourceCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    targetCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

dependencies {
    compileOnly("dev.espi:ProtectionStones:2.10.6")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.14")
}
