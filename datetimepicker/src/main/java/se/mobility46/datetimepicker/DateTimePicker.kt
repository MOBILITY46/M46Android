package se.mobility46.datetimepicker

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val DATE = "date"

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePicker : Fragment(), View.OnClickListener {
    private lateinit var button: Button

    private lateinit var dp: DatePickerDialog
    private lateinit var tp: TimePickerHelper

    private var date: LocalDateTime? = null
        set(value) {
            if (this::button.isInitialized) {
                this.button.text = this.digits(value)
                field = value
            }
        }

    var maxDate: LocalDateTime? = null
        set(value) {
            if (this::dp.isInitialized) {
                value?.let {
                    this.setMaximum(it)
                }
                field = value
            }
        }

    var minDate: LocalDateTime? = null
        set(value) {
            if (this::dp.isInitialized) {
                value?.let {
                    this.setMinimum(it)
                }
                field = value
            }
        }

    interface Listener {
        fun valueChanged(picker: DateTimePicker, date: LocalDateTime?)
        fun initialized()
    }

    var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(DATE)?.let {
                this.date = OffsetDateTime.parse(it).toLocalDateTime()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.listener?.initialized()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date_time_picker, container, false)
        this.button = view.findViewById(R.id.btn)
        this.button.text = "00:00"

        this.dp = DatePickerDialog(this.requireContext())

        this.maxDate?.let {
            this.setMaximum(it)
        }

        this.minDate?.let {
            this.setMinimum(it)
        }

        this.dp.setOnDateSetListener { _, y, m, d ->
            val year = y
            val month = m
            val day = d

            this.maxDate?.let {
                this.setMaximum(it)
            }

            this.minDate?.let {
                this.setMinimum(it)
            }

            this.tp = TimePickerHelper(this.requireContext(), true, false) { _, h, m ->
                this.date = LocalDateTime.of(year, month, day, h, m)
                this.listener?.valueChanged(this, this.date)
            }

            if (date == null) {
                this.tp.showDialog(0, 0)
            } else this.tp.showDialog(date!!.hour, date!!.minute)
        }

        this.button.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        if (v == button) {
            dp.show()
        }
    }

    private fun setMinimum(date: LocalDateTime) {
        val instant = date.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()
        this.dp.datePicker.minDate = instant.toEpochMilli()
    }

    private fun setMaximum(date: LocalDateTime) {
        val instant = date.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()
        this.dp.datePicker.maxDate = instant.toEpochMilli()
    }

    private fun digits(date: LocalDateTime?): String {
        if (date == null) {
            return "00:00"
        }
        return date.format(DateTimeFormatter.ofPattern("HH:mm")).toString()
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