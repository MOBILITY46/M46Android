package se.mobility46.licenseplate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatTextView

class LicensePlate(
    private val ctx: Context,
    private val attrs: AttributeSet) : AppCompatTextView(ctx, attrs) {

    private val paint: Paint = Paint()
    private val path: Path = Path()

    private val hint = "ABC123"

    var listener: InteractionListener? = null
    var identifier: String? = null


    var value: String = hint
        set(value) {
            this.text = when (value.isEmpty()) { true -> null else -> value }
            field = value
        }

    interface InteractionListener {
        fun licensePlateValueChanged(value: String)
        fun onLicensePlateDialogShow(dialog: LicensePlateDialogFragment)
    }

    init {
        isAllCaps = true
        isFocusable = false
        isActivated = false
        setBackgroundResource(R.drawable.plate)
        setTextColor(ContextCompat.getColor(ctx, R.color.black))
        setPadding(45, 7, 15, 7)
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(ctx, R.color.blue)
        paint.strokeWidth = 1f
        clipToOutline = true
    }

    override fun onDraw(canvas: Canvas?) {
        val h = height.toFloat()

        path.moveTo(0f, 0f)
        path.lineTo(35f, 0f)
        path.lineTo(35f, h)
        path.lineTo(0f, h)
        path.lineTo(0f, 0f)

        canvas?.drawPath(path, paint)

        super.onDraw(canvas)
    }

    fun toggle() {
        /*
        val dialog = createDialog {
            listener?.valueChanged(it)
        }
        dialog.show()
         */

        this.listener?.onLicensePlateDialogShow(this.createDialog())
    }



    private fun createDialog(): LicensePlateDialogFragment {
        var title = resources.getString(R.string.copy_title)
        if (identifier != null) {
            title = title.plus(" ${resources.getString(R.string.copy_for)} $identifier")
        }

        val message = resources.getString(R.string.copy_message)

        return LicensePlateDialogFragment.newInstance(title, message)
        /*
        val dialog = AlertDialog.Builder(ctx)

        var title = resources.getString(R.string.copy_title)
        if (listener?.licensePlateLabel != null) {
           title = title.plus(" ${resources.getString(R.string.copy_for)} ")
            .plus(listener?.licensePlateLabel)
        }

        dialog.setTitle(title)
        dialog.setMessage(R.string.copy_message)

        val input = EditText(ctx)

        input.filters = arrayOf(
            InputFilter.LengthFilter(maxCharacterCount),
            InputFilter.AllCaps(),
            InputFilter { s, _, _, _, _, _ ->
                s.replace(Regex("[^A-Za-z0-9 ]"), "")
            }
        )
        
        input.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val v = input.text.toString()
                    value = v
                    onSuccess.invoke(v)
                    true
                }
                else -> false
            }
        }

        input.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        input.setText(value)
        input.hint = hint
        input.setSingleLine()

        dialog.setView(input)

        dialog.setPositiveButton(R.string.ok) { _, _ ->
            val v = input.text.toString()
            value = v
            onSuccess.invoke(v)
        }

        dialog.setNegativeButton(R.string.cancel) { d, _ ->
            d.dismiss()
        }

        input.requestFocus()
        return dialog.create()

         */
    }
}

