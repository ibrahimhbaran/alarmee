import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.nativeCocoaPods)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = ProjectConfiguration.Alarmee.namespace
    compileSdk = ProjectConfiguration.Alarmee.compileSDK

    defaultConfig {
        minSdk = ProjectConfiguration.Alarmee.minSDK

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = ProjectConfiguration.Compiler.javaCompatibility
        targetCompatibility = ProjectConfiguration.Compiler.javaCompatibility
    }
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("mobile") {
                withAndroidTarget()
                withIos()
            }

            group("nonMobile") {
                withJvm()
                withJs()
                withWasmJs()
            }
        }
    }

    androidTarget {
        publishLibraryVariants("release")

        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(ProjectConfiguration.Compiler.jvmTarget))
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "alarmee"
            isStatic = true
        }
    }

    cocoapods {
        ios.deploymentTarget = ProjectConfiguration.iOS.deploymentTarget
        noPodspec()
        pod("FirebaseMessaging")
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js(IR) {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kmpkit)

            // Coroutines
            implementation(libs.kotlin.coroutines.core)

            // Compose
            implementation(compose.foundation)
        }

        val mobileMain by getting {
            dependencies {
                implementation(libs.firebase.messaging)
            }
        }

        iosMain {
            dependsOn(mobileMain)
        }

        androidMain {
            dependsOn(mobileMain)

            dependencies {
                implementation(libs.android.startup)
            }
        }

        val nonMobileMain by getting

        jvmMain {
            dependsOn(nonMobileMain)
        }

        wasmJsMain {
            dependsOn(nonMobileMain)
        }

        jsMain {
            dependsOn(nonMobileMain)
        }
    }
}

// region Publishing

group = ProjectConfiguration.Alarmee.Maven.group
version = ProjectConfiguration.Alarmee.versionName

mavenPublishing {
    publishToMavenCentral(host = SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    // Only enable signing if the flag is true
    if (findProperty("mavenPublishing.signAllPublications")?.toString()?.toBoolean() == true) {
        signAllPublications()
    }

    coordinates(groupId = group.toString(), artifactId = ProjectConfiguration.Alarmee.Maven.name.lowercase(), version = version.toString())
    configure(
        platform = KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        )
    )

    pom {
        name = ProjectConfiguration.Alarmee.Maven.name
        description = ProjectConfiguration.Alarmee.Maven.description
        url = ProjectConfiguration.Alarmee.Maven.packageUrl

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        issueManagement {
            system = "GitHub Issues"
            url = "${ProjectConfiguration.Alarmee.Maven.packageUrl}/issues"
        }

        developers {
            developer {
                id = ProjectConfiguration.Alarmee.Maven.Developer.id
                name = ProjectConfiguration.Alarmee.Maven.Developer.name
                email = ProjectConfiguration.Alarmee.Maven.Developer.email
            }
        }

        scm {
            connection = "scm:git:git://${ProjectConfiguration.Alarmee.Maven.gitUrl}"
            developerConnection = "scm:git:ssh://${ProjectConfiguration.Alarmee.Maven.gitUrl}"
            url = ProjectConfiguration.Alarmee.Maven.packageUrl
        }
    }
}

// endregion Publishing
