plugins { 
  java
}

subprojects {
 
  apply(plugin = "java")

  java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(arrayOf("-deprecation", "--enable-preview"))
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
