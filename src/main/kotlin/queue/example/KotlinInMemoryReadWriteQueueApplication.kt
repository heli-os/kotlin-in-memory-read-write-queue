package queue.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinInMemoryReadWriteQueueApplication

fun main(args: Array<String>) {
    runApplication<KotlinInMemoryReadWriteQueueApplication>(*args)
}
