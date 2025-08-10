import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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

android {
    namespace = "com.github.naz013.localization"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

// Define the Gradle task to verify StringKeys uniqueness
val verifyStringKeysUniqueness by tasks.registering(JavaExec::class) {
    group = "verification"
    description = "Verifies that all 'key' properties in StringKeys.kt are unique using reflection."

    classpath = sourceSets.getByName("jvmMain").runtimeClasspath // Or sourceSets.jvmMain.get().runtimeClasspath
    mainClass.set("com.github.naz013.localization.util.StringKeysVerifierKt")

    // If your desktop target is named 'jvm', use:
    dependsOn(kotlin.targets.getByName("jvm").compilations.getByName("main").compileKotlinTask)
}

// Ensure verification runs before the library's assemble tasks
afterEvaluate {
    // Make the general 'assemble' task of this library module depend on the global verification task.
    // This will cover 'assembleDebug', 'assembleRelease', etc.
    tasks.findByName("assemble")?.dependsOn(":verifyStringKeysUniqueness")

    // If it's a pure Kotlin/JVM library, you might also want to explicitly link it to the 'jar' task
    tasks.findByName("jar")?.dependsOn(":verifyStringKeysUniqueness")
}
