val deps = Dependencies

private const val junitJupiterVersion = "5.6.0"

object Dependencies {
    const val kotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val junit5Jupiter = "org.junit.jupiter:junit-jupiter:$junitJupiterVersion"
    const val junit5JupiterApi = "org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion"
    const val junit5JupiterParams = "org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion"
    const val kluent = "org.amshove.kluent:kluent:1.60"
}