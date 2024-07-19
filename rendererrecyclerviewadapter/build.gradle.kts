plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.android.kotlin)
	id("maven-publish")
	id("signing")
}

android {
	namespace = "com.github.vivchar.rendererrecyclerviewadapter"

	defaultConfig {
		minSdk = libs.versions.minSdkVersion.get().toInt()
		compileSdk = libs.versions.compileSdkVersion.get().toInt()
		version = "3.0.2"
//		version = "3.0.2-SNAPSHOT"
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

signing {
	sign(publishing.publications["release"])
}

/**
 * https://issues.sonatype.org/browse/OSSRH-54875
 * https://issues.sonatype.org/browse/OSSRH-67154
 * Move local.properties to gradle.properties if you have a problems with SIGNING
 */
publishing {
	publications {
		create<MavenPublication>("release") {
			afterEvaluate {
				from(components["release"])
			}

			groupId = "com.github.vivchar"
			artifactId = "RendererRecyclerViewAdapter"

			pom {
				packaging = "aar"
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
	}

	/**
	 * ./gradlew publish -Ptoken='<token>'
	 *
	 * Check Snapshots builds here:
	 * https://oss.sonatype.org/content/repositories/snapshots/com/github/vivchar/RendererRecyclerViewAdapter/
	 *
	 * Check Release builds here:
	 * https://oss.sonatype.org/#stagingRepositories
	 * */
	repositories {
		maven {
			val releasesUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
			val snapshotsUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
			url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl)

			credentials {
				username = "Ud8EE3UN"
				password = findProperty("token") as String?
			}
		}
	}
}