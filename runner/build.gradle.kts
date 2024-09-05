plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation(project(":lib"))
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))
    implementation(project(":formatter"))
    implementation(project(":linter"))
}
