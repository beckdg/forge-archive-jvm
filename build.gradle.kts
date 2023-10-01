plugins {
    java
    `java-library`
    checkstyle
    jacoco
    id("net.ltgt.errorprone") version "4.1.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf(
        "-parameters",
        "-Xlint:all,-processing",
    ))
}

dependencies {
    errorprone("com.google.errorprone:error_prone_core:2.36.0")
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")
    testCompileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")
}

tasks.test {
    useJUnitPlatform()
    maxParallelForks = Runtime.getRuntime().availableProcessors().coerceAtLeast(2)
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

checkstyle {
    toolVersion = "10.21.2"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}

tasks.check {
    dependsOn(tasks.checkstyleMain, tasks.checkstyleTest)
}
