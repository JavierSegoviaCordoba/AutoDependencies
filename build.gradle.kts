plugins {
    kotlin("jvm") version "1.3.71"
    `java-gradle-plugin`
    `maven-publish`
    id("com.palantir.idea-test-fix") version "0.1.0"
    id("com.gradle.plugin-publish") version "0.11.0"
}

group = "com.javiersc.resources.autodependencies"
version = "0.0.2"

repositories {
    jcenter()
}

dependencies {
    implementation(deps.kotlinJdk8)

    testImplementation(deps.junit5Jupiter)
    testImplementation(deps.junit5JupiterApi)
    testImplementation(deps.junit5JupiterParams)
    testImplementation(deps.kluent)
    testImplementation(gradleTestKit())
}

tasks {
    test { useJUnitPlatform() }
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }
}

sourceSets {
    getByName("main").java.srcDir("main")
    getByName("test").java.srcDir("test")
}

gradlePlugin {
    plugins {
        create("autodependencies") {
            id = "com.javiersc.resources.autodependencies"
            displayName = "AutoDependencies generate a Kotlin file with all your dependencies"
            description = "This Kotlin file is autogenerated from a txt file where you only copy " +
                    "and paste the dependencies"
            implementationClass = "com.javiersc.resources.AutoDependenciesPlugin"
        }
    }
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main { allSource })
}

publishing {
    val localRepoPath = "${rootProject.rootDir}/repo"
    repositories {
        maven(url = uri(localRepoPath))
    }
    publications {
        create<MavenPublication>("maven") {
            logger.info("saving to $localRepoPath")
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

pluginBundle {
    website = "https://github.com/JavierSegoviaCordoba/AutoDependencies/"
    vcsUrl = "https://github.com/JavierSegoviaCordoba/AutoDependencies/"
    tags = listOf("auto", "dependency", "dependencies")
}