plugins {
    id("buildlogic.kotlin-library-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":lib"))

    testImplementation(project(":lexer"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
