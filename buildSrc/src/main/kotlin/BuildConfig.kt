import org.gradle.api.JavaVersion

object BuildConfig {
    object SdkVersion {
        const val compile = 30
        const val min = 21
        const val target = 30
    }

    const val versionCode = 1
    const val versionName = "0.1.0"

    val javaVersion = JavaVersion.VERSION_1_8
}
