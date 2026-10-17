java {
    sourceCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    targetCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    maven("https://maven.elmakers.com/repository/")
}

dependencies {
    compileOnly("net.sacredlabyrinth.Phaed:PreciousStones:15.0")
}
