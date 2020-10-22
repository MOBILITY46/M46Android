package se.mobility46.m46android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.*
import se.mobility46.licenseplate.LicensePlate
import se.mobility46.licenseplate.LicensePlateDialogFragment

class TabOneFragment : androidx.fragment.app.Fragment(), LicensePlate.InteractionListener {

    override fun licensePlateValueChanged(value: String) {
        GlobalScope.launch(Dispatchers.Main) {
            runAsyncTask(value)
        }
    }

    override fun onLicensePlateDialogShow(dialog: LicensePlateDialogFragment) {
        val fm = this.fragmentManager
        if (fm != null) {
            dialog.show(fm, "license_plate_edit_fragment")
        }
    }

    private suspend fun runAsyncTask(value: String) {
        delay(1000)
        val toast = Toast
            .makeText(context, value, Toast.LENGTH_LONG)
        toast.show()
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
