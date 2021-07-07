plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}


val javaJwtVersion = extra.get("javaJwtVersion")

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


    implementation(project(":core.domain"))
    implementation(project(":core.application"))

    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("io.jkratz.springmediatr:spring-mediatr:1.1-RELEASE")

    testImplementation("org.springframework.boot:spring-boot-starter-test")




}
