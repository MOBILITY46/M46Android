package se.mobility46.datetimepicker

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.*
import java.time.format.DateTimeFormatter

private const val DATE = "date"

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePicker(val ctx: Context) : Fragment(), View.OnClickListener {
    private lateinit var button: Button
    private lateinit var subText: TextView

    private lateinit var dp: DatePickerDialog
    private lateinit var tp: TimePickerHelper

    var date: LocalDateTime? = null
        set(value) {
            if (this::button.isInitialized) {
                this.button.text = this.digits(value)
                this.subText.text = this.dateString(value)
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
        this.subText = view.findViewById(R.id.sub_text)

        this.dp = DatePickerDialog(ctx)

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

            this.tp = TimePickerHelper(this.ctx, true) { _, h, m ->
                var date = LocalDateTime.of(year, month + 1, day, h, m)

                if (this.minDate != null && date.isBefore(this.minDate)) {
                    date = this.minDate
                }

                if (this.maxDate != null && date.isAfter(this.maxDate)) {
                    date = this.maxDate
                }

                this.date = date
                this.listener?.valueChanged(this, date)
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
        val offset = ZoneOffset.systemDefault().rules.getOffset(date)
        val instant = date.toInstant(offset)
        this.dp.datePicker.minDate = instant.toEpochMilli()
    }

    private fun setMaximum(date: LocalDateTime) {
        val offset = ZoneOffset.systemDefault().rules.getOffset(date)
        val instant = date.toInstant(offset)
        this.dp.datePicker.maxDate = instant.toEpochMilli()
    }

    private fun digits(date: LocalDateTime?): String {
        if (date == null) {
            return "00:00"
        }
        return date.format(DateTimeFormatter.ofPattern("HH:mm")).toString()
    }

    private fun dateString(date: LocalDateTime?): String {
        if (date == null) {
            return ""
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context, date: LocalDateTime?) =
            DateTimePicker(context).apply {
                arguments = Bundle().apply {
                    date?.let {
                        putString(DATE, it.format(DateTimeFormatter.ISO_DATE_TIME))
                    }
                }
            }
    }
}