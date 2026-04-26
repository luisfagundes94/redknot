// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
}

subprojects {
    detekt {
        config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = true
        parallel = true
        ignoreFailures = false
    }

    pluginManager.withPlugin("io.gitlab.arturbosch.detekt") {
        dependencies {
            add("detektPlugins", "io.nlopez.compose.rules:detekt:0.5.1")
        }
    }
}
