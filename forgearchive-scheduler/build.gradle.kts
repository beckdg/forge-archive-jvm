plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-concurrency"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
