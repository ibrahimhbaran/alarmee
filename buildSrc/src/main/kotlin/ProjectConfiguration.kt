import org.gradle.api.JavaVersion

/**
 * @author Vivien Mahe
 * @since 23/07/2022
 */

object ProjectConfiguration {

    object Alarmee {
        const val packageName = "com.tweener.alarmee"
        const val versionName = "1.6.4"
        const val namespace = "$packageName.android"
        const val compileSDK = 35
        const val minSDK = 24

        object Maven {
            const val name = "Alarmee"
            const val description = "A Kotlin/Compose Multiplatform library for effortless alarm and local notification scheduling on both Android and iOS."
            const val group = "io.github.tweener"
            const val packageUrl = "https://github.com/Tweener/alarmee"
            const val gitUrl = "github.com:Tweener/alarmee.git"

            object Developer {
                const val id = "Tweener"
                const val name = "Vivien Mahé"
                const val email = "vivien@tweener-labs.com"
            }
        }
    }

    object Compiler {
        val javaCompatibility = JavaVersion.VERSION_21
        val jvmTarget = javaCompatibility.toString()
    }

    object iOS {
        const val deploymentTarget = "12.0"
    }
}
