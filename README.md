# Gradle - an informal introduction

[libGDX](./game)
[spring boot](./spring)
[maven init](./init/maven-init)
[gradle init](./init/gradle-init)
[gradle init from an existing maven project](./init/maven-to-gradle)

> This will cover most features you're likely to run into along with some slightly
> *extra* uses.

## What is Gradle and how to get started

> *Many-two* ways to install gradle

- [SDKMAN!](https://sdkman.io/)
  - `sdk install gradle 6.8.3`
- [asdf](https://asdf-vm.com)
  - `asdf install gradle 6.8.3`
- [homebrew](https://brew.sh)
  - `brew install gradle`
- [pacman](https://archlinux.org)
  - `pacman -S gradle`
- [chocolatey](https://chocolatey.org/)
  - `choco install gradle`
- Sounds like a job for someone else
  - `./gradlew`
  - `gradlew.bat`
  - *IntelliJ waves hands*

## projects

[docs](https://docs.gradle.org/current/userguide/multi_project_builds.html)

```groovy
allprojects {
  ...
}

subprojects {
  ...
}

project(":core") {
  ...
}

project(":desktop") {
  ...
}
```

## properties / variables

[docs](https://docs.gradle.org/current/userguide/build_environment.html)
[ext docs](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.ExtraPropertiesExtension.html)

```groovy
allprojects {
  ext {
    appName = "game"
    slf4jVersion = '1.7.30'
    gdxVersion = '1.9.13'
  }
}
```

---

```properties
# File: gradle.properties
org.gradle.daemon=true
org.gradle.jvmargs=-Xms128m -Xmx1500m
org.gradle.configureondemand=false
```

---

```bash
$ ./gradlew build -PappName=anotherGame
```

## source sets

[docs](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceSet.html)

- managing your classpath
- loading resources
- external dependencies
  - repositories (where you get them)

> If you stick to the defaults it should *Just Work™*

```groovy
project(":desktop") {
  sourceSets {
    main {
      resources {
        srcDirs += [
          project(':core').sourceSets.main.resources
        ]
      }
    }
  }
}
```

## dependencies

[docs](https://docs.gradle.org/current/userguide/declaring_dependencies.html)

```groovy
project(":desktop") {
  dependencies {
    implementation project(":core")
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
    testImplementation platform('org.junit:junit-bom:5.7.1')
    testImplementation('org.junit.jupiter:junit-jupiter')
  }
  repositories {
    mavenCentral()
  }
}
```

## plugins

[docs](https://docs.gradle.org/current/userguide/plugins.html)

```groovy
plugins {
  id "io.freefair.lombok" version "5.3.0" apply false
  id "com.diffplug.spotless" version "5.9.0" apply false
}
```

### spotless

[google-java-format](https://github.com/google/google-java-format)
[spotless](https://github.com/diffplug/spotless/)

```groovy
subprojects {
  apply plugin: "com.diffplug.spotless"

  spotless {
    java {
      importOrder('java', 'java', 'com.badlogic.gdx', 'com.chasbob', '')
      removeUnusedImports()
      googleJavaFormat('1.9')
    }
    groovyGradle {
      target '*.gradle'
      greclipse()
    }
  }
}
```

### application

[docs](https://docs.gradle.org/current/userguide/application_plugin.html)

```groovy
project(":desktop") {
  apply plugin: "application"

  application {
    getMainClass().set("com.chasbob.game.desktop.DesktopLauncher")
  }
}
```

## Continuous integration (CI)

> mirroring to [GitLab](git-teaching.cs.bham.ac.uk) from GitHub
{.is-danger}


[A better example](https://github.com/Chasbob/Gradle-for-Java/blob/main/.github/workflows/java.yaml)

```yaml
name: Java

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.11
      uses: actions/setup-java@v1
      with:
        java-version: 1.11
    - name: Build
      run: ./gradlew assemble
    - name: Lint
      run: ./gradlew spotlessCheck
    - name: Test
      run: ./gradlew test
```

## A rather busy 'starting point'

```bash
├── .github
│  └── workflows
│     └── java.yaml
├── build.gradle
├── core
│  ├── lombok.config
│  └── src
│     ├── main
│     │  ├── java
│     │  │  └── com
│     │  │     └── chasbob
│     │  │        └── game
│     │  │           └── Game.java
│     │  └── resources
│     │     └── logo.jpg
│     └── test
│        └── java
│           └── com
│              └── chasbob
│                 └── game
│                    └── GameTest.java
├── desktop
│  ├── lombok.config
│  └── src
│     └── main
│        ├── java
│        │  └── com
│        │     └── chasbob
│        │        └── game
│        │           └── desktop
│        │              └── DesktopLauncher.java
│        └── resources
├── gradle
│  └── wrapper
│     └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

