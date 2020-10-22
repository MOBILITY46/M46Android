package se.mobility46.licenseplate

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

const val TITLE = "title"
const val MESSAGE = "message"

class LicensePlateDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(title: String, message: String) = LicensePlateDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(MESSAGE, message)
            }
        }
    }

    private lateinit var viewModel: LicensePlateDialogViewModel
    private var textView: AutoCompleteTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.license_plate_dialog_fragment, container, false)

        arguments?.let {
            v.findViewById<TextView>(R.id.title).text = it.getString(TITLE)
            v.findViewById<TextView>(R.id.message).text = it.getString(MESSAGE)
        }

        this.textView = v.findViewById(R.id.value)

        v.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            this.dismiss()
        }

        return v
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LicensePlateDialogViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.plates.observe(this.viewLifecycleOwner) { plates ->
            this.textView?.setText(plates.last)
        }
        viewModel.load(this.requireContext())

        viewModel.addPlate(this.requireContext(), "QWERTY")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}