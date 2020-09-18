plugins {
    id(BuildPlugins.androidApplication) version BuildVersions.agp apply false
    id(BuildPlugins.kotlinAndroid) version BuildVersions.kotlin apply false
    id(BuildPlugins.hilt) version BuildVersions.hilt apply false
    id(BuildPlugins.detekt) version BuildVersions.detekt
    id(BuildPlugins.ktlintGradle) version BuildVersions.ktlintGradle
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    apply {
        plugin(BuildPlugins.ktlintGradle)
        plugin(BuildPlugins.detekt)
    }
    ktlint {
        version.set(Versions.ktlint)
        verbose.set(true)
        android.set(true)
    }
    detekt {
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
