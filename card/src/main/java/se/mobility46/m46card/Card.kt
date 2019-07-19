package se.mobility46.m46card

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout


class Card constructor(context: Context, attrs: AttributeSet?)
    : FrameLayout(context, attrs) {

    enum class FlipState {
        FRONT, BACK
    }

    interface OnCardInteractionListener {
        fun onCardFlipped(state: FlipState)
    }

    var listener: OnCardInteractionListener? = null

    private val animOut: AnimatorSet =
        AnimatorInflater.loadAnimator(context, R.animator.flip_out) as AnimatorSet

    private val animIn: AnimatorSet =
        AnimatorInflater.loadAnimator(context, R.animator.flip_in) as AnimatorSet

    private var frontLayout: View? = null
    private var backLayout: View? = null
    private var flipDuration: Int = 300
    private var state: FlipState = FlipState.FRONT

    init {

        attrs?.let {
            val attrs = context.obtainStyledAttributes(it,
                R.styleable.card_attrs, 0, 0)

            flipDuration = attrs.getInt(R.styleable.card_attrs_flip_duration, 300)

            attrs.recycle()
        }

        println(flipDuration)

        animIn.duration = flipDuration.toLong()
        animOut.duration = flipDuration.toLong()

        animIn.removeAllListeners()
        animIn.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (state == FlipState.FRONT) {
                    frontLayout?.visibility = View.VISIBLE
                    backLayout?.visibility = View.GONE
                }

                if (state == FlipState.BACK) {
                    frontLayout?.visibility = View.GONE
                    backLayout?.visibility = View.VISIBLE
                }
                listener?.onCardFlipped(state)
            }
        })
    }


    fun flip() {
        if (animIn.isRunning || animOut.isRunning || childCount != 2) {
            return
        }

        frontLayout?.visibility = View.VISIBLE
        backLayout?.visibility = View.VISIBLE

        if (state == FlipState.FRONT) {
            backLayout?.alpha = 0.0f
            animIn.setTarget(backLayout)
            animOut.setTarget(frontLayout)
        }

        if (state == FlipState.BACK) {
            frontLayout?.alpha = 0.0f
            animIn.setTarget(frontLayout)
            animOut.setTarget(backLayout)
        }

        state = if (state == FlipState.FRONT) FlipState.BACK else FlipState.FRONT
        animIn.start()
        animOut.start()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeViews()
        setCameraDistance()
    }

    private fun initializeViews() {
        if (childCount > 0) {
            backLayout = getChildAt(0)
        }
        if (childCount > 1) {
            frontLayout = getChildAt(1)
            frontLayout?.visibility = View.VISIBLE
            backLayout?.visibility = View.GONE
        }

        if (childCount > 2) {
            throw IllegalStateException("Card can only host two direct children!")
        }
    }

    private fun setCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        frontLayout?.cameraDistance = scale
        backLayout?.cameraDistance = scale
    }


}
