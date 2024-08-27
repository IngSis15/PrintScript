plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "PrintScript"
include("lexer")
include("interpreter")
include("runner")
include("parser")
include("ast")
include("token")
include("untitled")
include("linter")
include("txtReader")
