pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "ConfigurableMenus"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven ("https://repo.papermc.io/repository/maven-public/")
        maven ( "https://oss.sonatype.org/content/groups/public/")
        maven ("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven ("https://repo.aikar.co/content/groups/aikar/")
        maven ("https://repo.codemc.org/repository/maven-public/")
        maven ("https://jitpack.io")
    }
    versionCatalogs {
        create("libs") {
            from("com.lapzupi.dev:lapzupi-catalog:0.0.7")

            library("junit-jupiter", "org.junit.jupiter","junit-jupiter").versionRef("junit")
        }
    }
}