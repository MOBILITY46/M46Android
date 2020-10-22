package se.mobility46.licenseplate

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

private const val PREFS_KEY = "m46-plates"

class LicensePlateDialogViewModel : ViewModel() {
   val plates = MutableLiveData<LinkedList<String>>()

   fun addPlate(ctx: Context, value: String) {
      val newPlates = this.plates.value
      if (newPlates != null) {
         newPlates.addFirst(value)
         if (newPlates.count() > 3) {
             newPlates.removeLast()
         }
         val prefs = ctx.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
         val editor = prefs.edit()
         editor.putString("plates", newPlates.joinToString { "," })
         editor.apply()
         this.plates.value = newPlates
         this.plates.postValue(newPlates)
      }
   }

   fun load(ctx: Context) {
      val prefs = ctx.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
      prefs.getString("plates", null)?.let {
         val plates = it.split(",") as LinkedList<String>
         this.plates.value = plates
          this.plates.postValue(plates)
      }
   }
}