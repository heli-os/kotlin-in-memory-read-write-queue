package queue.example.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

/**
 * @author Theo
 * @since 2025/03/07
 */
@EnableScheduling
@Configuration
class SchedulingConfiguration {
    /**
     * 실제 사용 할 TaskScheduler 빈으로 대체
     */
    @Bean
    fun scheduler(): TaskScheduler = ThreadPoolTaskScheduler()
}
