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
    id("com.adarshr.test-logger") version "3.0.0"
}


allprojects {
    group = "com.esgi"
    version = "0.0.1-SNAPSHOT"
    apply(plugin = "com.adarshr.test-logger")
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

        testlogger {
            // pick a theme - mocha, standard, plain, mocha-parallel, standard-parallel or plain-parallel
            theme = com.adarshr.gradle.testlogger.theme.ThemeType.STANDARD
            // set to false to disable detailed failure logs
            showExceptions=true
            // set to false to hide stack traces
            showStackTraces=true
            // set to true to remove any filtering applied to stack traces
            showFullStackTraces=false
            // set to false to hide exception causes
            showCauses=true
            // set threshold in milliseconds to highlight slow tests
            slowThreshold=2000
            // displays a breakdown of passes, failures and skips along with total duration
            showSummary=true
            // set to true to see simple class names
            showSimpleNames=false
            // set to false to hide passed tests
            showPassed=true
            // set to false to hide skipped tests
            showSkipped=true
            // set to false to hide failed tests
            showFailed=true
            // enable to see standard out and error streams inline with the test results
            showStandardStreams=false
            // set to false to hide passed standard out and error streams
            showPassedStandardStreams=true
            // set to false to hide skipped standard out and error streams
            showSkippedStandardStreams=true
            // set to false to hide failed standard out and error streams
            showFailedStandardStreams=true
        }
    }

}

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


