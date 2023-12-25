plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-journal"))
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-transaction"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
