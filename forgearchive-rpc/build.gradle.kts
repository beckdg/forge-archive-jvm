plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-protocol"))
    api(project(":forgearchive-buffer"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
