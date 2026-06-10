import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech)
    id("signing")
}

kotlin {
    jvm()

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
                }
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "KmpInAppReview"
            isStatic = true
        }
    }

    sourceSets {
        jvmMain.dependencies {}

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.play.review)
            implementation(libs.play.review.ktx)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.coroutines.test)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "io.github.froyder.kmpinappreview"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val localProps = gradleLocalProperties(rootDir, providers)

group = "io.github.froyder"
version = "1.0.0"

signing {
    val signingKeyId = localProps.getProperty("signing.keyId") ?: ""
    val signingPassword = localProps.getProperty("signing.password") ?: ""
    val signingSecretKeyFile = localProps.getProperty("signing.secretKeyFile") ?: ""

    if (signingKeyId.isNotEmpty() && signingSecretKeyFile.isNotEmpty()) {
        signing {
            useInMemoryPgpKeys(
                signingKeyId,
                file(signingSecretKeyFile).readText(),
                signingPassword
            )
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.froyder",
        artifactId = "kmp-inapp-review",
        version = "1.0.0"
    )

    pom {
        name.set("KMP In-App Review")
        description.set("A Kotlin Multiplatform library that wraps Google Play In-App Review and SKStoreReviewController under a unified coroutine-friendly API.")
        url.set("https://github.com/Froyder/kmp-inapp-review")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }

        developers {
            developer {
                id.set("froyder")
                name.set("Ilia Khomutskikh")
                email.set("homutskih@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/Froyder/kmp-inapp-review")
            connection.set("scm:git:git://github.com/Froyder/kmp-inapp-review.git")
            developerConnection.set("scm:git:ssh://git@github.com/Froyder/kmp-inapp-review.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}