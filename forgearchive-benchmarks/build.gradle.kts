plugins {
    id("me.champeau.jmh") version "0.7.2"
    `java-library`
}
dependencies {
    api(project(":forgearchive-core"))
    api(project(":forgearchive-buffer"))
    api(project(":forgearchive-compression"))
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-chunking"))
    api(project(":forgearchive-dedup"))
    api(project(":forgearchive-index"))
    api(project(":forgearchive-pack"))
    implementation(libs.jmh.core)
    annotationProcessor(libs.jmh.generator)
}
