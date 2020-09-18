package se.mobility46.lib

import android.content.Context;
import java.lang.Exception
import java.util.*

class System {

    enum class ErrorType {
        APP_VERSION,
    }

    class SystemException(type: ErrorType) : Exception(type.toString())

    companion object {


        private fun appVersion(ctx: Context) : String? {
            try {
                val info = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
                return info.versionName.substring(0, info.versionName.indexOf(" ")).trim()
            } catch (e: Exception) {
                throw SystemException(ErrorType.APP_VERSION)
            }
        }

        fun description(context: Context, appName: String) : Result<String> {
            return Result {

                val appVersion = appVersion(context)
                val deviceOS = "Android"
                val androidVersion = android.os.Build.VERSION.RELEASE
                val deviceModel = android.os.Build.MANUFACTURER

                val lang = Locale.getDefault().language

                var language = "en"
                if (lang.contains("sv", true)) {
                    language = "sv"
                }

                return@Result "$appName/$appVersion $deviceOS/$androidVersion Android; model=$deviceModel; lang=$language;"
            }
        }
    }
}