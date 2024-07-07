plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = libs.versions.sdk.compile.get().toInt()
    buildToolsVersion = libs.versions.buildTools.get()

    buildFeatures.compose = true

    defaultConfig {
        applicationId = "com.mxalbert.sharedelements.demo"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionName = project.property("VERSION_NAME") as String
        versionCode = 1
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(projects.sharedElements)
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.material.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.android)
//    implementation(libs.androidx.ui.tooling.preview.android)
//    implementation(libs.androidx.navigation.compose)

    debugImplementation ("androidx.compose.ui:ui-tooling:1.6.8")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.8")
}
