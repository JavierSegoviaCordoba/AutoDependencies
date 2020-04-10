package com.javiersc.resources.extensions

import java.nio.file.Files
import java.nio.file.Path

/**
 * Author: https://github.com/pablisco
 */
interface FileTreeScope {
    operator fun String.invoke(block: FileTreeScope.() -> Unit)
    fun folder(path: String, block: FileTreeScope.() -> Unit)
    operator fun String.invoke()

    @Suppress("MemberVisibilityCanBePrivate")
    fun emptyFile(path: String)

    @Suppress("KDocUnresolvedReference")
    infix operator fun String.plusAssign(content: String)
    fun file(path: String, content: String)
}

internal class DefaultFileTreeScope(private val path: Path) :
    FileTreeScope {

    override operator fun String.invoke(block: FileTreeScope.() -> Unit) {
        folder(this, block)
    }

    override fun folder(path: String, block: FileTreeScope.() -> Unit) {
        val branch = this.path.resolve(path)
        branch.createDirectories()
        DefaultFileTreeScope(branch).block()
    }

    override operator fun String.invoke() {
        emptyFile(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    override // Api
    fun emptyFile(path: String) {
        file(path, "")
    }

    @Suppress("KDocUnresolvedReference")
    override infix operator fun String.plusAssign(content: String) {
        file(this, content)
    }

    override fun file(path: String, content: String) {
        val finalPath = this.path.resolve(path)
        finalPath.parent.createDirectories()
        Files.write(finalPath, content.toByteArray())
    }
}
