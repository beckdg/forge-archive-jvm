plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-journal"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
