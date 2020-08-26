package se.mobility46.m46android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import se.mobility46.datetimepicker.DateTimePicker
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TabTwoFragment : androidx.fragment.app.Fragment(), DateTimePicker.Listener {

    val dateTimePicker = DateTimePicker.newInstance(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_two, container, false)


        dateTimePicker.listener = this

        this.fragmentManager?.inTransaction {
            add(R.id.fragment_container, dateTimePicker)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = TabTwoFragment()
    }

    override fun valueChanged(picker: DateTimePicker, date: LocalDateTime?) {
        println("valueChanged(${date})")
    }

    override fun initialized() {
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        val now = LocalDateTime.now()
        val maxDate = now.plusMinutes(60)
//        val maxDate = OffsetDateTime.parse("2020-08-26T13:00:00+02:00", formatter).toLocalDateTime()

        dateTimePicker.minDate = now
        dateTimePicker.maxDate = maxDate
    }
}
