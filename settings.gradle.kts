pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "ConfigurableMenus"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
            library("vault-api", "com.github.MilkBowl:VaultAPI:1.7.1")
            
            version("adventure", "4.16.0")
            library("adventure-api", "net.kyori","adventure-api").versionRef("adventure")
            library("adventure-minimessage", "net.kyori", "adventure-text-minimessage").versionRef("adventure")
            library("adventure-bukkit", "net.kyori:adventure-platform-bukkit:4.3.2")
            
            library("placeholder-api", "me.clip:placeholderapi:2.11.5")
            
            library("configurate-hocon", "org.spongepowered:configurate-hocon:4.1.2")
            
            library("kraken-core", "com.github.sarhatabaot:KrakenCore:1.7.3")
            library("commands", "co.aikar:acf-paper:0.5.1-SNAPSHOT")
            library("gui", "dev.triumphteam:triumph-gui:3.1.7")
            library("nbt-api", "de.tr7zw:item-nbt-api:2.12.3")
            library("bstats", "org.bstats:bstats-bukkit:3.0.2")
            
            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:5.9.0")
        }
    }
}