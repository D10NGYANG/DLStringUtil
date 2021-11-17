plugins {
    kotlin("jvm") version "1.5.31"
    id("maven")
    id("maven-publish")
}

group = "com.d10ng"

repositories {
    mavenCentral()
    maven("https://jitpack.io" )
    maven("https://repo1.maven.org/maven2/")
}

kotlin {
    java {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    // 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    // 反射
    api("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    // 加密
    api("com.soywiz.korlibs.krypto:krypto:2.4.7")
    // 深复制
    // https://mvnrepository.com/artifact/com.bennyhuo.kotlin.reflect/deepcopy-reflect
    //api("com.bennyhuo.kotlin.reflect:deepcopy-reflect:1.5.0")
}