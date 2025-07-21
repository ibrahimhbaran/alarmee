import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
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
            baseName = "alarmee-push"
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
            implementation(project(":alarmee"))
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
