package me.vickychijwani.kotlinkoans

import com.google.gson.annotations.SerializedName

// single koan
data class Koan(
        val id: String,
        val name: String,

        // code blocks in this are marked <code data-lang="text/x-kotlin"> OR <code data-lang="text/x-java">
        @SerializedName("help")
        val descriptionHtml: String,

        val files: List<KoanFile>
) {
    companion object {
        val EMPTY = Koan("", "", "", listOf())
    }
}


data class KoanFile(
        @SerializedName("publicId")
        val id: String,

        val name: String,

        @SerializedName("text")
        val contents: String,

        val modifiable: Boolean,

        // possible (expected) solutions; only one file in every Koan contains this
        val solutions: List<String>?
)

// top-level list of koans
typealias KoanFolders = List<KoanFolder>
data class KoanFolder(
        val id: String,

        val name: String,

        // null if `projects` is non-empty
        val levels: List<KoanLevel>?,

        // e.g., Kotlin Koans/Introduction => [Hello World, Java to Kotlin conversion, ...]
        // empty if `subfolders` is non-empty
        @SerializedName("projects")
        val koans: List<KoanMetadata>,

        // e.g., Kotlin Koans => [Introduction, Conventions, ...]
        // empty if `projects` is non-empty
        @SerializedName("childFolders")
        val subfolders: List<KoanFolder>
)

data class KoanMetadata(
        @SerializedName("publicId")
        val id: String,
        val name: String
)

data class KoanLevel(
        val projectsNeeded: Int,                // total # of koans needed to finish this level
        val color: String                       // color of tick on progress bar
)

// run a koan
data class KoanRunInfo(
        val id: String,
        val name: String,

        // list of *modifiable* files with code to run
        val files: List<KoanFile>,

        // list of names of non-modifiable files
        val readOnlyFileNames: List<String>,

        // don't need to worry about these, defaults are fine
        val args: String = "",
        val compilerVersion: Any? = null,
        val confType: String = "junit",
        val originUrl: String = id
)

data class KoanRunResults(
        // may not exist in case of e.g., syntax errors
        val testResults: Map<String, List<KoanRunTestResult>>?,
        val errors: Map<String, List<KoanRunError>>
)

data class KoanRunTestResult(
        val status: String      // OK or ERROR (not implemented) or FAIL (wrong answer)
)

data class KoanRunError(
        val severity: String,   // "ERROR", ...??
        val className: String,  // "red_wavy_line", ...??
        val message: String,
        val interval: KoanCodeInterval
)


// utility types
data class KoanCodeInterval(
        val start: KoanCodePosition,
        val end: KoanCodePosition
)

data class KoanCodePosition(
        val line: Int,
        @SerializedName("ch")
        val column: Int
)