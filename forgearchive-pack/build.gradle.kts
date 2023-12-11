plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-compression"))
    api(project(":forgearchive-dedup"))
    api(project(":forgearchive-chunking"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
