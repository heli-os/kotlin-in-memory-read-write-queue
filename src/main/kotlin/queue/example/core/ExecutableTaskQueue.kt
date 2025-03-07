package queue.example.core

import org.springframework.stereotype.Component
import queue.example.core.model.ExecutableTask
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Theo
 * @since 2025/03/07
 */
@Component
class ExecutableTaskQueue {
    private val items: ConcurrentHashMap<ExecutableTask.Key, ExecutableTask> = ConcurrentHashMap()

    val size: Int
        get() = items.size

    /**
     * 큐가 꽉차지 않았으면 작업을 큐에 추가합니다.
     *
     * 1. 같은 작업이 이미 큐에 있는 경우
     *   1-1 추가하려는 작업이 이미 큐에 있는 정보보다 최신인 경우 업데이트
     *   1-2 이미 큐에 있는 정보가 더 최신인 경우 그대로 유지
     * 2. 처음 추가하려는 작업인 경우
     *   2-1 큐가 꽉찼으면 추가하지 않음
     *   2-2 큐가 꽉차지 않았으면 추가함
     *
     * @return 작업이 큐에 추가 됐으면 true 꽉차서 추가가 안됐으면 false (2-1 경우만 false)
     * @see [BlockingQueue.offer]
     */
    fun offer(task: ExecutableTask): Boolean {
        val computedTask =
            items.compute(task.key) { _, oldTask ->
                if (oldTask != null) {
                    if (oldTask.shouldBeUpdatedTo(task)) {
                        println("1-1. oldTask: $oldTask, task: $task")
                        task
                    } else {
                        println("1-2. oldTask: $oldTask, task: $task")
                        oldTask
                    }
                } else {
                    if (items.size >= MAX_QUEUE_SIZE) {
                        println("2-1. oldTask: $oldTask, task: $task")
                        null
                    } else {
                        println("2-2. oldTask: $oldTask, task: $task")
                        task
                    }
                }
            }
        println("computedTask: $computedTask")
        return computedTask != null
    }

    /**
     * 큐에 있는 모든 요소를 가져온 뒤 제거함.
     */
    fun pollAll(): List<ExecutableTask> = mutableListOf<ExecutableTask>().also { drainTo(it) }

    /**
     * @see [BlockingQueue.drainTo]
     */
    private fun drainTo(c: MutableCollection<ExecutableTask>) {
        items.keys
            .toList()
            .asSequence()
            .map { items.remove(it) }
            .filterNotNull()
            .forEach { c.add(it) }
    }

    private fun ExecutableTask.shouldBeUpdatedTo(other: ExecutableTask): Boolean = timestamp < other.timestamp

    companion object {
        private const val MAX_QUEUE_SIZE = 100
    }
}
