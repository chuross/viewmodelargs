package com.github.chuross.viewmodelargs.compiler.extention

fun String.normalize(): String {
    return trim()
            .replace("-", "_")
            .split("_")
            .filter { it.isNotBlank() }
            .mapIndexed { index, s -> if (index == 0) s else s.capitalize() }
            .joinToString("")
}