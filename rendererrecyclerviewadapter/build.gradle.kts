plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.android.kotlin)
}

android {
	namespace = "com.github.vivchar.rendererrecyclerviewadapter"

	defaultConfig {
		minSdk = libs.versions.minSdkVersion.get().toInt()
		compileSdk = libs.versions.compileSdkVersion.get().toInt()
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_18
		targetCompatibility = JavaVersion.VERSION_18
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_18.toString()
	}
}

dependencies {
	implementation(libs.androidx.annotation)
	implementation(libs.androidx.recyclerview)
}

//apply from: '../maven.gradle'