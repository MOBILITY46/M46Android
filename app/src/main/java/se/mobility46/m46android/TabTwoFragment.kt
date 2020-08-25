package se.mobility46.m46android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import se.mobility46.datetimepicker.DateTimePicker

class TabTwoFragment : androidx.fragment.app.Fragment() {

    val dateTimePicker = DateTimePicker.newInstance(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_two, container, false)

        this.fragmentManager?.inTransaction {
            add(R.id.fragment_container, dateTimePicker)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = TabTwoFragment()
    }
}
