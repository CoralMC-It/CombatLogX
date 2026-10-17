java {
    sourceCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    targetCompatibility = if (providers.gradleProperty("platform").orNull == "paper") JavaVersion.VERSION_25 else JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    maven("https://nexus.sirblobman.xyz/jitpack-mirror/")
}

dependencies {
    compileOnly("com.github.WhipDevelopment:CrashClaim:c697d3e9ef")
}
