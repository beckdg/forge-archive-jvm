plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-protocol"))
    api(project(":forgearchive-compression"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
