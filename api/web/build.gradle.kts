plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

val javaJwtVersion = extra.get("javaJwtVersion")
val jaxbApiVersion = extra.get("jaxbApiVersion")


dependencies {
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("javax.xml.bind:jaxb-api:$jaxbApiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation(project(":infrastructure"))
    implementation(project(":core.domain"))
    implementation(project(":core.application"))


    // broker de messages
    implementation ("io.jkratz.springmediatr:spring-mediatr:1.1-RELEASE")
}
