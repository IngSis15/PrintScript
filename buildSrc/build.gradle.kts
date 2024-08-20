plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.1")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.8.3")
}