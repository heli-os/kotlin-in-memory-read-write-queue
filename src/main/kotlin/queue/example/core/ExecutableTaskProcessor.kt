package queue.example.core

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import queue.example.core.model.ExecutableTask

/**
 * @author Theo
 * @since 2025/03/07
 */
@Component
class ExecutableTaskProcessor(
    private val noOpTaskExecutor: NoOpTaskExecutor,
    private val queue: ExecutableTaskQueue,
) : AutoCloseable {
    @Scheduled(fixedRate = FLUSH_INTERVAL_MILLIS, initialDelay = FLUSH_INTERVAL_MILLIS)
    fun flushSchedule() {
        flush()
    }

    /**
     * 작업을 처리 함.
     * 큐에 작업을 모아 놨다가 주기적으로 실행.
     */
    fun process(task: ExecutableTask) {
        if (!queue.offer(task)) {
            println("큐가 꽉 차서 작업을 처리하지 못함")
        }

        if (queue.flushNeeded) {
            flush()
        }
    }

    private fun flush() {
        val tasks = queue.pollAll()
        if (tasks.isNotEmpty()) {
            tasks.forEach { noOpTaskExecutor.execute(it) }
        }
    }

    override fun close() {
        flush()
    }

    private val ExecutableTaskQueue.flushNeeded: Boolean get() = size >= MAX_EXECUTABLE_TASK_SIZE

    companion object {
        private const val MAX_EXECUTABLE_TASK_SIZE = 50
        private const val FLUSH_INTERVAL_MILLIS: Long = 5 * 1000
    }
}
