plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":core.domain"))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation ("io.jkratz.springmediatr:spring-mediatr:1.1-RELEASE")
}
