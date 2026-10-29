@file:Suppress("PropertyName", "VariableNaming")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.iridium)
    alias(libs.plugins.iridium.publish)
    alias(libs.plugins.iridium.upload)
    alias(libs.plugins.fabric.loom)
}

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://teamvoided.org/releases") { content { includeGroup("org.teamvoided") } }
    maven("https://teamvoided.org/snapshots") { content { includeGroup("org.teamvoided") } }
    maven("https://maven.fzzyhmstrs.me/") { name = "FzzyMaven"; content { includeGroup("me.fzzyhmstrs") } }
    maven("https://maven.terraformersmc.com/") { // ModMenu, EMI
        name = "Terraformers"
        content {
            includeGroup("com.terraformersmc")
            includeGroup("dev.emi")
        }
    }
    maven("https://maven.ryanhcode.dev/releases") { // ImGuiMC
        name = "RyanHCode Maven"
        content { includeGroup("foundry.imguimc") }
    }
    maven("https://api.modrinth.com/maven") { content { includeGroup("maven.modrinth") } }
    // Mixson
    exclusiveContent { forRepository { maven("https://jitpack.io") }; filter { includeGroup("com.github.ramixin") } }
    // YACL
    maven("https://maven.isxander.dev/releases")
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(fileTree("libs"))

    minecraft(libs.minecraft)
    // Dependencies
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.fabric.kotlin)
    implementation(libs.fzzy.config)
    implementation(libs.tomlkt)
    // Compatibility
//    implementation(libs.dusks.and.dungeons)
    implementation(libs.enderscape)
    implementation("maven.modrinth:trim-patcher:1.0-mc26.2")
    implementation("dev.isxander:yet-another-config-lib:3.9.5+26.2-fabric")
    implementation("maven.modrinth:lithostitched:1.7.13-fabric-26.2")
    // Runtime
    implementation(libs.modmenu)
    // Testing
//    implementation(libs.creative.works)
//    implementation(libs.imguimc)
}

val username = "vDev"
val uuid = iridium.fetchUUID(username) // Dev & vDev will always be null

loom {
    splitEnvironmentSourceSets()
//    accessWidenerPath.set(File("src/client/resources/${iridium.modId}.classtweaker"))

    mods {
        register(iridium.modId) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }

    runs {
        named("client") {
            programArgs("--username", username)
            uuid?.let { programArgs("--uuid", it) }
        }

        create("randomClient") {
            client()
            runDir("run")
            ideConfigGenerated(true)
        }

        create("TestWorld") {
            client()
            runDir("run")
            ideConfigGenerated(true)
            programArgs("--quickPlaySingleplayer", "test", "--username", username)
            uuid?.let { programArgs("--uuid", it) }
        }

        forEach { it.vmArgs("-XX:+AllowEnhancedClassRedefinition") }

    }
}

fabricApi {
    configureDataGeneration {
        client = true
        createRunConfiguration = true
        createSourceSet = true
        addToResources = true
        modId = iridium.modId + "_vdatagen"

        strictValidation = false
    }
}

tasks {
    val javaVersion = libs.versions.java.get()
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(javaVersion.toInt())
    }

    withType<KotlinCompile>().all {
        compilerOptions.jvmTarget = JvmTarget.fromTarget(javaVersion)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(javaVersion).toString()))
        withSourcesJar()
    }

    sourceSets.forEach { set ->
        named<ProcessResources>(set.processResourcesTaskName) {
            var expandProps = iridium.props.toMutableMap()
            iridium.appendLibsVersionProps(expandProps, File("libs.versions.toml"))
            filesMatching(
                listOf("pack.mcmeta", "fabric.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml")
            ) {
                expand(expandProps)
            }
            inputs.properties(expandProps)
        }
    }
}

publishScript {
    releaseRepository("TeamVoided", "https://maven.teamvoided.org/releases")
    publication(iridium.modId, isSnapshot = false)
    publishSources = true
}

uploadScript {
    debugMode = false

    modrinthId = "wyNemTYI"
//    curseId = "0"

    changelog = File("changelog.md").readText()

    version += libs.versions.minecraft.get()
    versionName = "${iridium.modName()} ${iridium.modVersion}"
    jarTask = tasks.jar.get()

    dependency("P7dR8mSH", "fabric-api")
    dependency("Ha28R6CL", "fabric-language-kotlin")
    dependency("hYykXjDp", "fzzy-config")
}
