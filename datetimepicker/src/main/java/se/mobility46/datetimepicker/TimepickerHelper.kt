package se.mobility46.datetimepicker

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import java.util.*

class TimePickerHelper(
    context: Context,
    is24HourView: Boolean,
    isSpinnerType: Boolean = false,
    callback: (v: View, h: Int, m: Int) -> Unit
) {
    private var dialog: TimePickerDialog

    init {
        val style = if (isSpinnerType) R.style.SpinnerTimePickerDialog else 0
        val cal = Calendar.getInstance()
        dialog = TimePickerDialog(context, style, callback,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), is24HourView)
    }

    fun showDialog(hour: Int, minute: Int) {
        dialog.updateTime(hour, minute)
        dialog.show()
    }
}