plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(project(":parser"))
    api(project(":lexer"))
    api(project(":interpreter"))
}
