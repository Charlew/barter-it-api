package barter.barter_it_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Clock
import java.time.ZoneId

@SpringBootApplication
class BarterItApiApplication

fun main(args: Array<String>) {
	runApplication<BarterItApiApplication>(*args)
}

@Bean
fun localClock(): Clock? {
	return Clock.system(ZoneId.of("Europe/Warsaw"))
}