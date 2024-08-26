plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.0")
    api(project(":parser"))
}
