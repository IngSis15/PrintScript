plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":token"))
    testImplementation(project(":parser"))
    testImplementation(project(":lexer"))
    implementation(project(":lib"))
    implementation("com.google.code.gson:gson:2.11.0")
}
