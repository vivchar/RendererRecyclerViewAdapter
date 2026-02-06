import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.android.kotlin)
	alias(libs.plugins.maven.publish)
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
}

dependencies {
	implementation(libs.androidx.annotation)
	implementation(libs.androidx.recyclerview)
}

mavenPublishing {
	configure(AndroidSingleVariantLibrary(variant = "release"))
	publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
	signAllPublications()

	coordinates("com.github.vivchar", "RendererRecyclerViewAdapter", "3.0.3")

	pom {
		name.set("RendererRecyclerViewAdapter")
		description.set("A single adapter with multiple view types for the whole project")
		url.set("https://github.com/vivchar/RendererRecyclerViewAdapter")

		licenses {
			license {
				name.set("The Apache License, Version 2.0")
				url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
			}
		}
		developers {
			developer {
				id.set("vivchar")
				name.set("Vitaly Vivchar")
				email.set("vivchar.vitaliy@gmail.com")
			}
		}
		scm {
			connection.set("scm:https://github.com/vivchar/RendererRecyclerViewAdapter.git")
			developerConnection.set("scm:git@github.com:vivchar/RendererRecyclerViewAdapter.git")
			url.set("https://github.com/vivchar/RendererRecyclerViewAdapter")
		}
	}
}
