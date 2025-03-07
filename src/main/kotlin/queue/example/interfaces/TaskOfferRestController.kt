package queue.example.interfaces

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import queue.example.core.ExecutableTaskProcessor
import queue.example.core.model.ExecutableTask
import java.time.Instant
import java.util.concurrent.ThreadLocalRandom

/**
 * @author Theo
 * @since 2025/03/07
 */
@RestController
class TaskOfferRestController(
    private val executableTaskProcessor: ExecutableTaskProcessor,
) {
    @GetMapping("/task-offer")
    fun offer() {
        val numberOfOneToTen = ThreadLocalRandom.current().nextInt(1, 11)
        val boundedTimestamp = Instant.now().minusSeconds(numberOfOneToTen.toLong())

        val task =
            ExecutableTask(
                id = numberOfOneToTen.toLong(),
                timestamp = boundedTimestamp,
                name = "task-$numberOfOneToTen",
                task = { println("task executed") },
            )
        executableTaskProcessor.process(task)
    }
}
