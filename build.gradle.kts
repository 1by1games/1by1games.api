import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaJwtVersion: String by project
val jaxbApiVersion : String by project

extra.apply {
    set("javaJwtVersion", javaJwtVersion)
    set("jaxbApiVersion", jaxbApiVersion)
}

repositories {
    mavenCentral()
}

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31"
}

java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    group = "com.esgi"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
tasks{
    bootJar{
        manifest{
            attributes["Start-Class"]="com.esgi.onebyone.OnebyoneApplication"
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "io.spring.dependency-management")

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }


}

