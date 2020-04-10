package com.javiersc.resources

import com.javiersc.resources.dependency.cleanDuplicates
import com.javiersc.resources.dependency.toDependenciesString
import com.javiersc.resources.dependency.toDependency
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import java.io.File

class AutoDependenciesPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        val rootDirPath = target.rootDir.path
        val buildSrcPath = File("$rootDirPath/buildSrc/").apply {
            if (!this.exists()) mkdirs()
        }
        File(buildSrcPath, "build.gradle.kts").apply {
            if (!this.exists()) {
                createNewFile()
                writeText(buildGradleKtsContent)
            }
        }
        val dependenciesTxtFile = File("$rootDirPath/dependencies.txt")

        if (!dependenciesTxtFile.exists()) dependenciesTxtFile.createNewFile()
        val kotlinFolder = File("$rootDirPath/buildSrc/src/main/kotlin").apply {
            if (!exists()) mkdirs()
        }
        val dependencyFile = File("$kotlinFolder/Dependencies.kt").apply {
            createNewFile()
        }

        target.gradle.afterProject {
            dependencyFile.writeText(
                dependenciesTxtFile.readLines()
                    .map(String::toDependency)
                    .cleanDuplicates()
                    .toDependenciesString()
            )

            println("\n${dependencyFile.absolutePath}")
            dependencyFile.readLines().forEach(::println)
        }
    }
}

private val buildGradleKtsContent: String
    get() = """
                plugins {
                    `kotlin-dsl`
                }
                
                repositories {
                    jcenter()
                }

                kotlinDslPluginOptions {
                    experimentalWarning.set(false)
                }
            """.trimIndent()