package se.mobility46.m46android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import se.mobility46.licenseplate.LicensePlate

class TabOneFragment : androidx.fragment.app.Fragment(), LicensePlate.InteractionListener {
    override var licensePlateLabel: String? = "TEST"

    override fun valueChanged(value: String) {
        println("valueChanged($value)")
    }

    lateinit var plate: LicensePlate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_one, container, false)

        plate = view.findViewById(R.id.plate)
        plate.listener = this

        view.setOnClickListener {
            plate.toggle()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = TabOneFragment()
    }
}
