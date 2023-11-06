plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-chunking"))
    api(project(":forgearchive-crypto"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
