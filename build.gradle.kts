plugins {
    java
    `maven-publish`
    alias(libs.plugins.bukkit.yml)
    alias(libs.plugins.shadow)
}

group = "com.lapzupi.dev.configurablemenus"
version = "0.4.0"

dependencies {
    compileOnly(libs.paper.api)

    compileOnly(libs.vault.api)
    compileOnly(libs.placeholder.api)

    library(libs.adventure.api)
    library(libs.adventure.minimessage)
    library(libs.adventure.bukkit)
    library(libs.configurate.hocon)

    implementation(libs.commands.paper)
    implementation(libs.triumph.gui)
    implementation(libs.bstats)
    implementation(libs.bundles.lapzupi.utils)
    
    testImplementation(libs.junit.jupiter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
    withSourcesJar()
}

bukkit {
    name = rootProject.name
    authors = listOf("Lapzupi Development Team", "sarhatabaot")
    main = "com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin"
    website = "https://dev.lapzupi.com"
    version = project.version.toString()
    description=  "A configurable menu plugin"
    
    depend = listOf("PlaceholderAPI", "Vault")
    softDepend = listOf("ItemsAdder", "Nova", "HeadsDatabase")
    apiVersion = "1.21"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupId
            artifactId = artifactId
            version = version

            from(components["java"])
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    
    shadowJar {
        archiveClassifier.set("")
    
        dependencies {
            exclude(dependency("net.kyori:.*:.*"))
        }
        
        relocate ("de.tr7zw.changeme.nbtapi", "com.lapzupi.dev.configurablemenus.nbt")
        relocate ("org.bstats", "com.lapzupi.dev.configurablemenus.metrics")
        relocate ("co.aikar.command", "com.lapzupi.dev.configurablemenus.acf")
        relocate ("co.aikar.locales", "com.lapzupi.dev.configurablemenus.locales")
    }
}
