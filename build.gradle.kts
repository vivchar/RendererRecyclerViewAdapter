plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.android.kotlin) apply false
//	alias(libs.plugins.nexus.plugin)
}

/**
 * ./gradlew publishReleasePublicationToSonatypeRepository -Ptoken="token"
 * ./gradlew findSonatypeStagingRepository closeSonatypeStagingRepository -Ptoken="token"
 * */
//nexusPublishing {
//	repositories {
//		sonatype {
//			group = "com.github.vivchar" /* for filters by description */
//			version = "3.0.3" /* for filters by description */
//			packageGroup = "com.github.vivchar"
//			username = "Ud8EE3UN"
//			password = findProperty("token") as String?
//		}
//	}
//}