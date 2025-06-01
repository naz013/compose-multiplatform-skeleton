import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvm()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "logging"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
//            implementation(libs.koin.android)
        }
        commonMain.dependencies {
//            implementation(project.dependencies.platform(libs.koin.bom))
//            implementation(libs.koin.core)
        }
        commonTest.dependencies {

        }
    }
}

android {
    namespace = "com.github.naz013.logging"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
