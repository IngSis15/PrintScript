plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(project(":ast"))
    api(project(":token"))
    api(project(":parser"))
    api(project(":lexer"))
    implementation("com.google.code.gson:gson:2.11.0")
}
