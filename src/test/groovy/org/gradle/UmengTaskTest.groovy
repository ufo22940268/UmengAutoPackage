package org.gradle

import com.bettycc.BatchInfo
import com.bettycc.UmengTask
import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class UmengTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.extensions.create("batchInfo", BatchInfo, "wefijwfiwf wefiwjfiwef wefjiwefjiwfj")
        def task = project.task('greeting', type: UmengTask)
        assertTrue(task instanceof UmengTask)
        task.greet()
    }
}
