plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
}

kotlin {
    jvm()

    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.github.naz013.datastore"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "datastore"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)

            // Optional dependencies
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(project(":logging"))
            implementation(project(":localization"))

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            // Optional dependencies
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

            // Optional dependencies
            implementation(libs.koin.test)
        }
    }
}
