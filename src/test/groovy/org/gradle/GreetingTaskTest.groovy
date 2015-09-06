package org.gradle

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class GreetingTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.extensions.create("batchInfo", BatchInfo, "wefijwfiwf wefiwjfiwef wefjiwefjiwfj")
        def task = project.task('greeting', type: GreetingTask)
        assertTrue(task instanceof GreetingTask)
        task.greet()
    }
}
