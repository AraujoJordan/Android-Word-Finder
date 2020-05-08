plugins {
    id("com.android.application")
    id("com.vanniktech.android.junit.jacoco")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")
    defaultConfig {
        applicationId = "com.araujo.jordan.wordfindify"
        testApplicationId = "$applicationId.test"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "2.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments = mapOf(
            "clearPackageData" to "true"
        )
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
            }
        }
        proguardFile("proguard-rules.pro")
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
            isMinifyEnabled = true
            isShrinkResources = true
        }
        getByName("release") {
            isTestCoverageEnabled = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/LICENSE*")
    }


    junitJacoco {
        jacocoVersion = "0.8.5"
//        ignoreProjects = arrayOf()
        excludes = listOf("jdk.internal.*")
        isIncludeNoLocationClasses = true
        isIncludeInstrumentationCoverageInMergedReport = true
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        unitTests.all(KotlinClosure1<Any, Test>({
            (this as Test).also {
                testLogging {
                    events("passed", "skipped", "failed", "standardOut", "standardError")
                    showStandardStreams = true
                    maxHeapSize = "1024m"
                }
            }
        }, this))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin Libs
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")

    //Network Libs
    implementation("com.squareup.okhttp3:okhttp:4.6.0")

    //Design Libs
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")
    implementation("com.google.android.material:material:1.2.0-alpha06")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("nl.dionsegijn:konfetti:1.2.0")

    //My personal Libs
    implementation("com.github.AraujoJordan:KtList:0.5.1")

    //Test libraries
    androidTestImplementation("com.github.AraujoJordan:latest:0.0.5") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
        exclude("com.android.support.test", "runner")
        exclude("com.android.support.test", "rules")
        exclude("androidx.test", "orchestrator")
        exclude("androidx.test.uiautomator", "uiautomator")
    }
    androidTestUtil("androidx.test:orchestrator:1.2.0")
    testCompileOnly("org.jetbrains:annotations:13.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.61")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.robolectric:robolectric:4.3.1")
    testImplementation("androidx.test:core:1.2.0")
    testImplementation("io.sniffy:sniffy-junit:3.1.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation("com.squareup.spoon:spoon-client:1.7.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }

}
