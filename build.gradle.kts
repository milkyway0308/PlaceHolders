plugins {
    kotlin("jvm") version "1.4.30"
    id("java")
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

repositories {
    mavenCentral()
    maven("https://maven.pkg.github.com/milkyway0308/CommandAnnotation") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }
}


dependencies {
    implementation(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
    implementation(files("V:\\Project\\Java\\IntelliJ\\Workspace - Individual\\ReflectedNBTWrapper2\\out\\artifacts\\ReflectedNBTWrapper_v1_2_4"))
    implementation("skywolf46:commandannotation:+")

}

publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/milkyway0308/PlaceHolders")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
        }
    }
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "placeholders"
            version = properties["version"] as String
            pom {
                url.set("https://github.com/milkyway0308/PlaceHolders.git")
            }
        }
    }
}
