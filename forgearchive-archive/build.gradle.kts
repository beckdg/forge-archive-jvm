plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-buffer"))
    api(project(":forgearchive-io"))
    api(project(":forgearchive-crypto"))
    api(project(":forgearchive-compression"))
    api(project(":forgearchive-manifest"))
    api(project(":forgearchive-metadata"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
