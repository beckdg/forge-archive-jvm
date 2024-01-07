plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-io"))
    api(project(":forgearchive-archive"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
