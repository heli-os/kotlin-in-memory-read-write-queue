package queue.example.core

import org.springframework.stereotype.Component
import queue.example.core.model.ExecutableTask

/**
 * @author Theo
 * @since 2025/03/07
 */
@Component
class NoOpTaskExecutor {
    fun execute(task: ExecutableTask) {
        println("NoOpTaskExecutor.execute: $task")
    }
}
