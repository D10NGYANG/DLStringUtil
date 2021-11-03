plugins {
    kotlin("jvm") version "1.5.31"
    java
    id("maven")
    id("maven-publish")
}

group = "com.d10ng"

repositories {
    mavenCentral()
    maven("https://jitpack.io" )
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib"))
    // 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
}