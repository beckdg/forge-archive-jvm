plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-pack"))
    api(project(":forgearchive-unpack"))
    api(project(":forgearchive-sync"))
    api(project(":forgearchive-query"))
    api(project(":forgearchive-recovery"))
    api(project(":forgearchive-validation"))
    api(project(":forgearchive-inspection"))
    api(project(":forgearchive-statistics"))
    api(project(":forgearchive-snapshot"))
    api(project(":forgearchive-patch"))
    api(project(":forgearchive-diff"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
