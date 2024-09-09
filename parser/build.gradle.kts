plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":lib"))
}
