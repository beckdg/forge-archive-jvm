plugins {
    `java-library`
}

dependencies {
    api(project(":forgearchive-archive"))
    api(project(":forgearchive-manifest"))
    api(project(":forgearchive-metadata"))
    api(project(":forgearchive-index"))
    api(project(":forgearchive-journal"))
    api(project(":forgearchive-transaction"))
    api(project(":forgearchive-snapshot"))
    api(project(":forgearchive-dedup"))
    api(project(":forgearchive-configuration"))
    api(project(":forgearchive-rpc"))
    api(project(":forgearchive-protocol"))
    api(project(":forgearchive-compression"))
    api(project(":forgearchive-virtualfs"))
    api(project(":forgearchive-recovery"))
    api(project(":forgearchive-patch"))
    api(project(":forgearchive-diff"))
    api(project(":forgearchive-stream"))
    api(project(":forgearchive-query"))
    api(project(":forgearchive-plugin"))
    api(project(":forgearchive-chunking"))
    api(project(":forgearchive-core"))
    implementation(libs.jazzer.api)
}

sourceSets {
    main {
        java {
            srcDir("${rootProject.projectDir}/fuzz")
        }
    }
}

val runtimeClasspathForFuzz = configurations.runtimeClasspath

tasks.register<Jar>("fuzzJar") {
    group = "build"
    description = "Fat jar containing fuzz harnesses and all module dependencies"
    archiveBaseName.set("forgearchive-fuzz")
    archiveVersion.set("")
    archiveClassifier.set("")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    dependsOn(tasks.classes)
    dependsOn(runtimeClasspathForFuzz)
    dependsOn(rootProject.subprojects.map { it.tasks.named("jar") })

    from(sourceSets.main.get().output)

    manifest {
        attributes["Main-Class"] = "dev.forgearchive.fuzz.ArchiveReaderFuzzer"
    }

    from({
        runtimeClasspathForFuzz.get()
            .filter { it.name.endsWith(".jar") }
            .map { zipTree(it) }
    })
}

tasks.named<Jar>("jar") {
    enabled = false
}
