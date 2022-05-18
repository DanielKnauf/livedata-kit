import org.gradle.api.JavaVersion

object BuildConfig {

    object SdkVersion {
        const val compile = 31
        const val min = 21
        const val target = 31
    }

    const val versionCode = 3
    const val versionName = "0.1.2"

    val javaVersion = JavaVersion.VERSION_1_8
}
