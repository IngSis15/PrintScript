# PrintScript

A modular Kotlin implementation of a small TypeScript-inspired language, developed as a university team project at Universidad Austral.

PrintScript includes a lexer, parser, semantic analyzer, interpreter, formatter, static analyzer and command-line interface. It supports language versions **1.0** and **1.1** through version-specific grammar and token configuration.

## Architecture

```text
Source → Lexer → Tokens → Parser → AST → Semantic analysis / Interpreter
                                  └──→ Static analysis
Tokens ──────────────────────────────→ Formatter
                    Runner + CLI coordinate the operations
```

| Module | Responsibility |
| --- | --- |
| [lexer](lexer) / [token](token) | Tokenization strategies and version-specific token definitions |
| [parser](parser) / [ast](ast) | Expression parsing, syntax tree and semantic checks |
| [interpreter](interpreter) | Evaluation, variable scopes and arithmetic strategies |
| [formatter](formatter) | Configurable token-based source formatting |
| [linter](linter) | Configurable rules for naming, printing and input expressions |
| [runner](runner) | Coordinates execution, validation, formatting and analysis |
| [cli](cli) | Clikt command-line interface |
| [lib](lib) | Shared input, output and source-position abstractions |

The code separates language rules from orchestration through factories, visitors and strategies. Tests cover tokenization, parsing, semantic validation, interpretation and configurable formatting/analysis.

## Build and test

Requires **JDK 21**. Use the checked-in Gradle wrapper:

```bash
git clone https://github.com/IngSis15/PrintScript.git
cd PrintScript
./gradlew build
./gradlew test
./gradlew ktlintCheck
./gradlew koverVerify
```

On Windows, use `gradlew.bat`. The build also installs the repository's pre-commit hook. Coverage verification is configured with an 80% minimum for included code; this is a configured threshold, not a claim about the latest measured coverage.

## CLI examples

Run these commands from the repository root:

```bash
./gradlew :cli:run --args="--help"
./gradlew :cli:run --args="execute $PWD/runner/src/test/resources/interpreter/1.0/test-hello/main.ps --version 1.0"
./gradlew :cli:run --args="validate $PWD/runner/src/test/resources/validate/1.0/valid-assignation.ps --version 1.0"
./gradlew :cli:run --args="analyze $PWD/runner/src/test/resources/linter/1.0/valid-camelcase/main.ps --config $PWD/cli/src/main/resources/defaultLinter.json"
./gradlew :cli:run --args="format $PWD/runner/src/test/resources/formatter/1.0/simple-test/main.ps --config $PWD/cli/src/main/resources/defaultFormatter.json --output $PWD/formatted.ps"
```

These Bash examples use absolute paths via `$PWD` so they also resolve from Gradle's CLI working directory. For `analyze` and `format`, pass an explicit `--config`; the default config paths are relative to the process working directory. Use each command's `--help` to inspect its arguments.

Small language example:

```typescript
let greeting: string = "Hello, PrintScript!";
println(greeting);
```

## CI and publishing

[GitHub Actions](.github/workflows/ci.yml) runs the Gradle build on pushes and pull requests. The repository also includes a [package publishing workflow](.github/workflows/release-package.yml) and GitHub Packages configuration.

## Project context

This is an academic language-tooling project built collaboratively, not a production compiler or a complete TypeScript implementation. Contributions belong to the team; see the repository's [contributors](https://github.com/IngSis15/PrintScript/graphs/contributors) and commit history for attribution.

