package dev.hbrown.bootcoroutineapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootCoroutineApiApplication

fun main(args: Array<String>) {
    runApplication<BootCoroutineApiApplication>(*args)
}

