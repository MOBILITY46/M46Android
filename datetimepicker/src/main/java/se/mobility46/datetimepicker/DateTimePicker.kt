package se.mobility46.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val DATE = "date"

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePicker : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var date: LocalDateTime? = null
    private lateinit var button: Button

    interface Listener {
        fun valueChanged(date: LocalDateTime?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(DATE)?.let {
                this.date = OffsetDateTime.parse(it).toLocalDateTime()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date_time_picker, container, false)
        this.button = view.findViewById(R.id.btn)
        this.button.text = this.digits()
        return view
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }

    fun setMinimum(date: LocalDateTime) {
        TODO("Not yet implemented")
    }

    fun setMaximum(date: LocalDateTime) {
        TODO("Not yet implemented")
    }

    private fun digits(): String {
        if (this.date == null) {
            return "00:00"
        }
        return "${this.date?.hour}:${this.date?.minute}"
    }

    companion object {
        @JvmStatic
        fun newInstance(date: LocalDateTime?) =
            DateTimePicker().apply {
                arguments = Bundle().apply {
                    date?.let {
                        putString(DATE, it.format(DateTimeFormatter.ISO_DATE_TIME))
                    }
                }
            }
    }
}