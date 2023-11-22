plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-api"))
    api(project(":forgearchive-cli"))
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(project(":forgearchive-api"))
    testImplementation(project(":forgearchive-cli"))
}
