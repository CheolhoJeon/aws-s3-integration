import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"

//    java-library
}

group = "org.charlie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("aws.sdk.kotlin:s3:0.17.5-beta")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.20")

//    implementation(platform("software.amazon.awssdk:bom:2.18.6"))
//    implementation("software.amazon.awssdk:kinesis")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}