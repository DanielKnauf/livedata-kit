import org.gradle.api.JavaVersion

object BuildConfig {
    object SdkVersion {
        const val compile = 30
        const val min = 21
        const val target = 30
    }

    const val versionCode = 2
    const val versionName = "0.1.1"

    val javaVersion = JavaVersion.VERSION_1_8
}
