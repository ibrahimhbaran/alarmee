[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
![gradle-version](https://img.shields.io/badge/gradle-8.5.2-blue?logo=gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Website](https://img.shields.io/badge/Author-vivienmahe.com-purple)](https://vivienmahe.com/)
[![X/Twitter](https://img.shields.io/twitter/follow/VivienMahe)](https://twitter.com/VivienMahe)

<br>

![Alarmee logo](https://github.com/user-attachments/assets/c5e72a35-6269-4b29-933e-6ed3e27900f1#gh-light-mode-only)
![Alarmee logo](https://github.com/user-attachments/assets/bcc0da27-1616-4758-a1cb-1d7601734988#gh-dark-mode-only)

---

A Kotlin Multiplatform library for effortless alarm and notification scheduling on both Android and iOS.

## ⚙️ Installation

In your `build.gradle.kts` file, add Maven Central to your repositories:
```Groovy
repositories {
    mavenCentral()
}
```

Then add Alarmee dependency to your module:

- With version catalog, open `libs.versions.toml`:
```Groovy
[versions]
alarmee = "1.0.0" // Check latest version

[libraries]
alarmee = { group = "io.github.tweener", name = "alarmee", version.ref = "alarmee" }
```

Then in your module `build.gradle.kts` add:
```Groovy
dependencies {
    implementation(libs.alarmee)
}
```

- Without version catalog, in your module `build.gradle.kts` add:
```Groovy
dependencies {
    val alarmee_version = "1.0.0" // Check latest version

    implementation("io.github.tweener:alarmee:$alarmee_version")
}
```

The latest version is: [![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)

## 👨‍💻 Contributing

We love your input and welcome any contributions! Please read our [contribution guidelines](https://github.com/Tweener/alarmee/blob/master/CONTRIBUTING.md) before submitting a pull request.

## 🪪 Licence

Alarmee is licensed under the [Apache-2.0](https://github.com/Tweener/alarmee?tab=Apache-2.0-1-ov-file#readme).
