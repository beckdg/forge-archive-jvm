plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
