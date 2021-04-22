import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS
val javaJwtVersion: String by project
val jaxbApiVersion : String by project

extra.apply {
    set("javaJwtVersion", javaJwtVersion)
    set("jaxbApiVersion", jaxbApiVersion)
}

buildscript {
    repositories {
        mavenCentral()
    }
}


plugins {
    id("org.springframework.boot") version "2.4.4"  apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"  apply false
    kotlin("jvm") version "1.4.31"  apply false
    kotlin("plugin.spring") version "1.4.31"  apply false
    kotlin("plugin.jpa") version "1.4.31" apply false
}


allprojects {
    group = "com.esgi"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        println("Configuring KotlinCompile  $name in project ${project.name}...")

        kotlinOptions {
            languageVersion = "1.4"
            apiVersion = "1.4"
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}


//configurations {
//    compileOnly {
//        extendsFrom(configurations.annotationProcessor.get())
//    }
//}
//tasks{
//    bootJar{
//        manifest{
//            attributes["Start-Class"]="com.esgi.onebyone.OnebyoneApplication"
//        }
//    }
//}

subprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
    apply(plugin = "io.spring.dependency-management")

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }


}

