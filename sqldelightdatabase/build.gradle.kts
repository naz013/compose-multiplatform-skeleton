import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()

    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.github.naz013.sqldelight"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sqldelight"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
            // Optional dependencies
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(project(":logging"))

            // Optional dependencies
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite.driver)
        }
        commonTest.dependencies {

            // Optional dependencies
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            srcDirs.setFrom("src/commonMain/sqldelight")
            packageName.set("com.github.naz013.sqldelightdatabase")
        }
    }
}
