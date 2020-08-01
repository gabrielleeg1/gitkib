import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.72"
}

group = "com.lorenzoog.gitkib"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("io.arrow-kt:arrow-core:0.10.4")
  implementation("io.arrow-kt:arrow-syntax:0.10.4")

  // kotlin
  arrayOf("reflect", "stdlib-jdk8").forEach {
    implementation(kotlin(it))
  }

  implementation("org.slf4j:slf4j-api:1.7.30")
  implementation("ch.qos.logback:logback-classic:1.2.3")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // kotlin coroutines
  arrayOf("core", "reactor").forEach {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-$it:1.3.7")
  }

  implementation("com.zaxxer:HikariCP:3.4.5")

  // exposed
  arrayOf("jdbc", "dao", "core").forEach {
    implementation("org.jetbrains.exposed:exposed-$it:0.24.1")
  }

  implementation("com.orbitz.consul:consul-client:1.4.0")

  implementation("com.auth0:java-jwt:3.4.0")

  // database driver
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2")

  // validation
  implementation("am.ik.yavi:yavi:0.4.0")

  // test
  testImplementation("junit:junit")
  testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
  testImplementation("org.mockito:mockito-all:1.10.19")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}
