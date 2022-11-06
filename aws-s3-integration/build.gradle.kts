import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"

    `java-library`
}

group = "org.charlie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // AWS SDK Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("aws.sdk.kotlin:s3:0.17.10-beta")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.20")

    // AWS SDK Java
//    implementation(platform("software.amazon.awssdk:bom:2.15.0"))
//    implementation("software.amazon.awssdk:kinesis")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}