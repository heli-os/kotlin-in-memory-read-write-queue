package queue.example.core.model

import java.time.Instant

/**
 * @author Theo
 * @since 2025/03/07
 */
data class ExecutableTask(
    val id: Long,
    val timestamp: Instant,
    val name: String,
    val task: () -> Unit,
) {
    val key: Key get() = Key(id)

    data class Key(
        val id: Long,
    )
}
