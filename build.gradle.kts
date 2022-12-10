plugins { 
  java
  id("com.adarshr.test-logger") version "3.2.0"
}

subprojects {
 
  apply(plugin = "java")
  apply(plugin = "com.adarshr.test-logger")

  java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  testlogger {
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
  }

  tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(arrayOf("-Xlint", "-Xlint:-preview", "--enable-preview"))
    options.isFork = true
  }

  tasks.withType<Test>().configureEach {
    jvmArgs = listOf("--enable-preview")
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    setForkEvery(100)
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
    testLogging.showStandardStreams = true
  }
}
