plugins {
    id("java")
    `kotlin-dsl`
}

group = "btpos.dj2addons.gradle"
version = "1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://nexus.gtnewhorizons.com/repository/public/")
    }
}

dependencies {
    implementation("com.gtnewhorizons:retrofuturagradle:1.3.35")
}

