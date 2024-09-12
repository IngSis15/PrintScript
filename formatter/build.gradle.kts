plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    testImplementation(project(":lexer"))
    implementation(project(":lib"))
    implementation(project(":token"))
    implementation("com.google.code.gson:gson:2.11.0")
}
