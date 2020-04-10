package com.javiersc.resources

import com.javiersc.resources.extensions.fileTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

internal class AutoDependenciesPluginTest {

    @BeforeEach
    fun `generate dependencies txt file`(@TempDir projectDir: Path) {
        projectDir.fileTree { file("dependencies.txt", dependenciesTxtContent) }
    }

    @Test
    fun `test dependencies`(@TempDir projectDir: Path) {
        projectDir.fileTree {
            file(
                path = "settings.gradle.kts",
                content = """plugins { id("com.javiersc.resources.autodependencies") }"""
            )
        }

        val result = projectDir.runGradleProjects()

        result.shouldBeSuccess()
    }
}

private val dependenciesTxtContent: String
    get() = """
                com.javiersc.resources:resource:9.8.9
                com.javiersc.resources:resource:9.8.9
                com.javiersc.resources:resource:9.8.8-alpha01
                com.javiersc.resources:resource:9.8.10
                com.javiersc.resources:resource:9.9.9
                com.javiersc.resources:resource:10.0.1
                com.javiersc.resources:resource:9.8.9-alpha03
                com.javiersc.resources:resource:9.9.9-alpha01
                com.javiersc.resources:resource:9.8.9-beta03
                com.javiersc.resources:resource:9.8.9-beta01
                com.javiersc.resources:resource:9.8.9-rc03
                com.javiersc.resources:network-response:9.8.9-alpha02
                com.javiersc.resources:network-response:9.8.9-beta01
                com.javiersc.resources:material-toast:9.8.9.release-beta01
                com.javiersc.resources:material-toast:9.8.9.release
            """.trimIndent()
