package se.mobility46.lib

import android.content.Context;
import java.lang.Exception
import java.util.*

class System {

    enum class ErrorType {
        PACKAGE_LABEL,
        APP_VERSION,
        DEVICE_OS,
        ANDROID_VERSION,
        DEVICE_MODEL,
        LANGUAGE
    }

    class SystemException(type: ErrorType) : Exception(type.toString())

    companion object {


        private fun appName(ctx: Context) : String {
            val nameId = ctx.applicationInfo.labelRes
            if (nameId == 0) {
                val nonLocalized = ctx.applicationInfo.nonLocalizedLabel
                if (nonLocalized != null) {
                    return nonLocalized.toString()
                } else {
                    throw SystemException(ErrorType.PACKAGE_LABEL)
                }
            }
            return ctx.getString(nameId)
        }

        private fun appVersion(ctx: Context) : String? {
            try {
                val info = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
                return info.versionName
            } catch (e: Exception) {
                throw SystemException(ErrorType.APP_VERSION)
            }
        }

        fun description(context: Context) : Result<String> {
            return Result {

                val appName = appName(context)
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