plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:4.2.2")

    api(project(":lib"))
    api(project(":parser"))
    api(project(":lexer"))
    api(project(":interpreter"))
    api(project(":formatter"))
    api(project(":linter"))
}

application {
    mainClass.set("AppKt")
}
