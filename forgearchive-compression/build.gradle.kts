plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-buffer"))
    api(project(":forgearchive-io"))
    implementation(libs.lz4.java)
    implementation(libs.zstd.jni)
    implementation(libs.commons.compress)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
