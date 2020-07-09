plugins {
  kotlin("jvm")
}

group = "com.lorenzoog.gitkib"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))

  // exposed dependencies
  implementation("org.jetbrains.exposed", "exposed-core", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-dao", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-jdbc", "0.24.1")

  // hikari dependency
  implementation("com.zaxxer", "HikariCP", "3.4.5")

  // database
  implementation("org.postgresql", "postgresql", "42.1.4")

  // dot-env dependency
  implementation("io.github.cdimascio", "java-dotenv", "5.2.1")

  testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}