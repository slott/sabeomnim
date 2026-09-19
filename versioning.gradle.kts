/**
 * Builds an Android version code from a YEAR.WEEK.RELEASE version string (e.g. 2026.15.0)
 * or MAJOR.MINOR.PATCH version string (e.g. 1.0.1).
 * Handles -SNAPSHOT (0), -RC* (1-98), and final release (99).
 */
fun buildVersionCode(versionName: String): Int {
    val cleanVersion = versionName.lowercase().replace("-", "")
    val parts = cleanVersion.split(".")
    
    // Extract YY from YYYY (e.g., 2026 -> 26) to prevent hitting the 2.1 Billion Play Store limit
    val yearPart = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val year = if (yearPart >= 2000) yearPart % 100 else yearPart 
    
    val week = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val releasePart = parts.getOrNull(2) ?: "0"
    
    var candidate = 99
    var release = 0

    if (releasePart.contains("snapshot")) {
        candidate = 0
        release = releasePart.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
    } else if (releasePart.contains("rc")) {
        val rcParts = releasePart.split("rc")
        release = rcParts.getOrNull(0)?.toIntOrNull() ?: 0
        candidate = rcParts.getOrNull(1)?.toIntOrNull() ?: 1
    } else {
        release = releasePart.toIntOrNull() ?: 0
    }

    // Format: YY WW RR CC (e.g., 01 00 01 99 -> 1,000,199 or 26 38 00 99 -> 26,380,099)
    return (year * 1000000) + (week * 10000) + (release * 100) + candidate
}

project.extra.set("buildVersionCode", { vName: String -> buildVersionCode(vName) })
