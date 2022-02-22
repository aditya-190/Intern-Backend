package com.bhardwaj.routes

fun main() {
    val processBuilder = ProcessBuilder(
        "venv/bin/python", "main.py", "1", "android developer", "India"
    )
    val process = processBuilder.start()
    val exitCode = process.waitFor()
    if(exitCode == 0) {
        val output = String(process.inputStream.readBytes())
        print("Done -> $output")

    } else {
        val output = String(process.errorStream.readBytes())
        print("Error -> $output")
    }
}
