rootProject.name = "forgearchive"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
    }
}

include(
    "forgearchive-core",
    "forgearchive-buffer",
    "forgearchive-io",
    "forgearchive-compression",
    "forgearchive-crypto",
    "forgearchive-manifest",
    "forgearchive-metadata",
    "forgearchive-archive",
    "forgearchive-pack",
    "forgearchive-unpack",
    "forgearchive-snapshot",
    "forgearchive-diff",
    "forgearchive-patch",
    "forgearchive-index",
    "forgearchive-query",
    "forgearchive-filesystem",
    "forgearchive-virtualfs",
    "forgearchive-stream",
    "forgearchive-transport",
    "forgearchive-network",
    "forgearchive-rpc",
    "forgearchive-protocol",
    "forgearchive-sync",
    "forgearchive-chunking",
    "forgearchive-dedup",
    "forgearchive-cache",
    "forgearchive-memory",
    "forgearchive-allocator",
    "forgearchive-scheduler",
    "forgearchive-concurrency",
    "forgearchive-transaction",
    "forgearchive-journal",
    "forgearchive-recovery",
    "forgearchive-plugin",
    "forgearchive-configuration",
    "forgearchive-validation",
    "forgearchive-inspection",
    "forgearchive-statistics",
    "forgearchive-api",
    "forgearchive-cli",
    "forgearchive-fuzz",
    "forgearchive-benchmarks",
    "forgearchive-examples",
    "forgearchive-integration-tests",
)
