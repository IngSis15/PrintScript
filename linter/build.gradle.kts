plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(project(":ast"))
    api(project(":token"))
    api(project(":txtReader"))
    api(project(":parser"))
    api(project(":lexer"))
    implementation("org.json:json:20230618")
}
