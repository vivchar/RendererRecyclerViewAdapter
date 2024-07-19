plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.android.kotlin)
}

android {
	namespace = "com.github.vivchar.example"

	defaultConfig {
		applicationId = libs.versions.applicationID.get()

		compileSdk = libs.versions.compileSdkVersion.get().toInt()
		minSdk = libs.versions.minSdkVersion.get().toInt()
		targetSdk = libs.versions.targetSdkVersion.get().toInt()
		versionCode = 1
		versionName = "1.0.0"

		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
}

dependencies {
	implementation(libs.androidx.annotation)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.ktx)
	implementation(libs.androidx.recyclerview)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.cardview)
	implementation(libs.androidx.material)
	implementation(libs.androidx.swiperefreshlayout)
	implementation(libs.androidx.coordinatorlayout)

	implementation(libs.glide.core)
	annotationProcessor(libs.glide.compiler)
	implementation(libs.glide.transformations)

	implementation(libs.rx.java)
	implementation(libs.rx.kotlin)
	implementation(libs.rx.android)

	implementation(libs.kotlin.stdlib.jdk7)

//	implementation(projects.network)
	implementation(project(":network"))
	implementation(project(":rendererrecyclerviewadapter"))
}