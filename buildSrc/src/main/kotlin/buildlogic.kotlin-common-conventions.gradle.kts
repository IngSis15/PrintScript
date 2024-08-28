/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")

    // Formater and linter for Kotlin code.
    id("org.jlleitschuh.gradle.ktlint")

    // Coverage checker
    id("org.jetbrains.kotlinx.kover")

    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.11.0")
    }

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}


tasks.register<Copy>("copyPreCommitHook") {
    description = "Copy pre-commit git hook from the scripts to the .git/hooks folder."
    group = "git hooks"
    outputs.upToDateWhen { false }
    from("$rootDir/hooks/pre-commit")
    into("$rootDir/.git/hooks/")
}

tasks.build {
    dependsOn("copyPreCommitHook")
}

ktlint {
    debug.set(false)
    verbose.set(false)
    android.set(false)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
}

kover {
    reports {
        filters {
            excludes {
                packages("ast")
                packages("token")
                packages("runner")
                packages("txtReader")
            }
        }
        verify {
            rule {
                minBound(80)
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lizlubelczyk/PrintScript")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            from(components["kotlin"])
        }
    }
}
