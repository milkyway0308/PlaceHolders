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
    maven(properties["reposilite.release"] as String)
    maven(properties["reposilite.spigot"] as String)
}


dependencies {
    compileOnly("org.spigotmc:spigot:1.12.2")
    compileOnly("skywolf46:refnbt:latest.release")
    compileOnly("skywolf46:commandannotation:+")
}

publishing {
    repositories {
        maven {
            name = "Reposilite"
            url = uri(properties["reposilite.release"] as String)
            credentials {
                username = properties["reposilite.user"] as String
                password = properties["reposilite.token"] as String
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }

        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/milkyway0308/PlaceHolders")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
            authentication {
                create<BasicAuthentication>("basic")
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
