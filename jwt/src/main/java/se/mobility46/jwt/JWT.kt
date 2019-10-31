package se.mobility46.jwt

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
data class JWT(val token: String = "") {

    fun decode(): Map<String, String> {
        val parts = token.split(".")
        val header = getJson(parts[0])
        val claims = getJson(parts[1])
        return mapOf(Pair("header", header), Pair("claims", claims))
    }

    private fun getJson(encoded: String): String {
        val bytes = Base64.getDecoder().decode(encoded)
        return String(bytes, Charsets.UTF_8)
    }
}