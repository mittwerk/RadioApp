pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// fun includeProjects(
//    directory: File,
//    path: String,
//    maxDepth: Int = 1,
// ) {
//    directory.listFiles().orEmpty().also { it.sort() }.forEach { file ->
//        if (file.isDirectory) {
//            val newPath = "$path:${file.name}"
//            val buildFile = File(file, "build.gradle.kts")
//            if (buildFile.exists()) {
//                include(newPath)
//                logger.lifecycle("Included project: $newPath")
//            } else if (maxDepth > 0) {
//                includeProjects(file, newPath, maxDepth - 1)
//            }
//        }
//    }
// }

rootProject.name = "RadioApp"
include(":app")
