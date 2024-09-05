plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.0")
    api(project(":parser"))
    api(project(":lexer"))
    api(project(":lib"))

    implementation("com.google.code.gson:gson:2.11.0")
}
