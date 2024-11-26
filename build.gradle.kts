plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "com.lapzupi.dev"
version = "0.3.3"

repositories {
    mavenCentral()
    maven ("https://repo.papermc.io/repository/maven-public/")
    maven ( "https://oss.sonatype.org/content/groups/public/")
    maven ("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven ("https://repo.aikar.co/content/groups/aikar/")
    maven ("https://repo.codemc.org/repository/maven-public/")
    maven ("https://jitpack.io")
}

dependencies {
    compileOnly(libs.paper.api)

    compileOnly(libs.vault.api)
    compileOnly(libs.placeholder.api)

    library(libs.adventure.api)
    library(libs.adventure.minimessage)
    library(libs.adventure.bukkit)
    library(libs.configurate.hocon)
    
    implementation(libs.kraken.core)
    implementation(libs.commands)
    implementation(libs.gui)
    implementation(libs.bstats)
    
    testImplementation(libs.junit.jupiter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
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
    apiVersion = "1.20"
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
