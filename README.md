# kotlin-in-memory-read-write-queue

```shell
while true; do curl -X GET http://localhost:8080/task-offer ; done
```

```shell
curl -X GET http://localhost:8080/task-offer
 ```

```shell
└── src
    └── main
        ├── kotlin
        │   └── queue
        │       └── example
        │           ├── KotlinInMemoryReadWriteQueueApplication.kt
        │           ├── configuration
        │           │   └── SchedulingConfiguration.kt
        │           ├── core
        │           │   ├── ExecutableTaskProcessor.kt
        │           │   ├── ExecutableTaskQueue.kt
        │           │   ├── NoOpTaskExecutor.kt
        │           │   └── model
        │           │       └── ExecutableTask.kt
        │           └── interfaces
        │               └── TaskOfferRestController.kt
        └── resources
            └── application.properties
```
