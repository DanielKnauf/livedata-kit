buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.toolsGradle)
        classpath(Plugins.kotlinGradle)
        classpath(Plugins.kotlinAndroidExtensions)
        classpath(Plugins.androidMaven)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = Constants.jitPackUrl)
    }
}

subprojects {
    apply { plugin("maven") }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
