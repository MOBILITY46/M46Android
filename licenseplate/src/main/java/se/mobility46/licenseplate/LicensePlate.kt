package se.mobility46.licenseplate

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class LicensePlate(private val ctx: Context, attrs: AttributeSet) : TextView(ctx, attrs) {

    private val paint: Paint = Paint()
    private val path: Path = Path()
    var listener: InteractionListener? = null

    var maxCharacterCount = 7

    private val hint = "ABC123"

    var value: String = hint
        set(value) {
            this.text = value
            field = value
        }

    interface InteractionListener {
        var licensePlateLabel: String?
        fun valueChanged(value: String)
    }

    init {
        isAllCaps = true

        setBackgroundResource(R.drawable.plate)
        setTextColor(ContextCompat.getColor(ctx, R.color.black))
        setPadding(45, 7, 15, 7)

        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(ctx, R.color.blue)
        paint.strokeWidth = 1f

        @RequiresApi
        clipToOutline = true
    }

    fun toggle() {
        val dialog = createDialog {
            listener?.valueChanged(it)
        }
        dialog.show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        listener = null
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

    private fun createDialog(onSuccess: (v: String) -> Unit): AlertDialog {
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
            AlphaNumericInputFilter()
        )

        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(value)
        input.hint = hint
        input.setSingleLine()

        dialog.setView(input)


        dialog.setPositiveButton(R.string.ok) { d: DialogInterface, _ ->
            val v = input.text.toString()
            if (v != value) {
                value = v
                onSuccess.invoke(value)
            }
            d.cancel()
        }

        dialog.setNegativeButton(R.string.cancel) { d: DialogInterface, _ ->
            d.dismiss()
        }

        input.requestFocus()
        return dialog.create()
    }

    inner class AlphaNumericInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {

            val builder = StringBuilder()

            for (i in start until end) {
                source?.get(i)?.let {
                    if (Character.isLetterOrDigit(it)) {
                        builder.append(it)
                    }
                }
            }

            val allCharactersValid = (builder.length == end - start)
            if (allCharactersValid) {
                return builder.toString()
            }
            return ""
        }

    }

}

