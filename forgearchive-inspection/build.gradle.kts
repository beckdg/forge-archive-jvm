plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-validation"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
