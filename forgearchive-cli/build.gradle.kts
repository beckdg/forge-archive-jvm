plugins {
    application
    `java-library`
}

application {
    mainClass.set("dev.forgearchive.cli.ForgeArchiveCli")
}

dependencies {
    api(project(":forgearchive-api"))
    implementation(libs.picocli)
    annotationProcessor(libs.picocli.codegen)
    implementation(libs.logback.classic)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
}
