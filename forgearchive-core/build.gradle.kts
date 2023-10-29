plugins {
    `java-library`
}

dependencies {
    api(libs.slf4j.api)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
