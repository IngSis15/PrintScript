plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:4.2.2")
    implementation("commons-io:commons-io:2.16.1")

    implementation(project(":runner"))
    implementation(project(":lib"))
}

application {
    mainClass.set("AppKt")
}
