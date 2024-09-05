plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    api(project(":ast"))
    api(project(":token"))
    api(project(":lib"))
}
