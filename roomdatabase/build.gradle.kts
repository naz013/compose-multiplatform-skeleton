import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
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
            baseName = "roomdatabase"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.room.ktx)

            // Optional dependencies
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(project(":logging"))

            implementation(libs.room.runtime)
            implementation(libs.room.paging)

            implementation(libs.sqlite.bundled)

            // Optional dependencies
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.room.testing)

            // Optional dependencies
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.github.naz013.roomdatabase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.room.common.jvm)
    ksp(libs.room.compiler)
}
