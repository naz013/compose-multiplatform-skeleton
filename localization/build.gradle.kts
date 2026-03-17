import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm()

    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.github.naz013.localization"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "localization"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(kotlin("reflect"))

            implementation(libs.kotlinx.coroutines.android)

            // Optional dependencies
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.components.resources)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

            implementation(project(":logging"))

            // Optional dependencies
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        jvmMain.dependencies {
            implementation(kotlin("reflect"))

            implementation(libs.kotlinx.coroutines.swing)
        }
        commonTest.dependencies {
            // Optional dependencies
            implementation(libs.koin.test)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.github.naz013.localization.resources"
    generateResClass = always
}
