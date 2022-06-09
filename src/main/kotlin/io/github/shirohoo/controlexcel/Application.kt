package io.github.shirohoo.controlexcel

import io.github.shirohoo.controlexcel.dummy.Dummy
import io.github.shirohoo.controlexcel.dummy.DummyJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Component
class Initializer(
    private val repository: DummyJpaRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val dummies = (1..1_000).map { Dummy(name = "dummy${it}") }.toList()
        repository.saveAll(dummies)
    }
}