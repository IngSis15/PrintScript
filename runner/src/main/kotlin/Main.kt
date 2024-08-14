package org.example

import org.example.runner.RunnerImplementation

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val runner = RunnerImplementation()
    runner.run("println(\"Hello, World!\");")

    runner.run("let x: number = 5;")

    runner.run("""
        let x: string = "Hola mundo";
        let y: string = "Hello world";
        println(x);
    """.trimIndent())
}