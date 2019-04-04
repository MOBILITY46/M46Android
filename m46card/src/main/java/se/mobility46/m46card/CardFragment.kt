package se.mobility46.m46card

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


class CardFragment : Fragment() {

    interface OnFragmentInteractionListener {
        fun onFlip()
    }

    companion object {

        fun newInstance() =
            CardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var listener: OnFragmentInteractionListener? = null
    private var frontVisible: Boolean = true

    private lateinit var animBack: AnimatorSet
    private lateinit var animFront: AnimatorSet
    private lateinit var frontLayout: FrameLayout
    private lateinit var backLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
        initializeAnimations()
        setCameraDistance()
    }

    override fun onStart() {
        super.onStart()

        view?.setOnClickListener { v -> flip(v) }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun flip(view: View) {
        if (frontVisible) {
            animFront.setTarget(frontLayout)
            animBack.setTarget(backLayout)

        } else {
            animFront.setTarget(backLayout)
            animBack.setTarget(frontLayout)
        }
        animFront.start()
        animBack.start()
        frontVisible = !frontVisible
        listener?.onFlip()
    }

    private fun initializeViews() {
        view?.let {
            frontLayout = it.findViewById(R.id.card_front)
            backLayout = it.findViewById(R.id.card_back)
        }
    }

    private fun initializeAnimations() {
        animFront = AnimatorInflater.loadAnimator(this.context, R.animator.front) as AnimatorSet
        animBack = AnimatorInflater.loadAnimator(this.context, R.animator.back) as AnimatorSet
    }

    private fun setCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        frontLayout.setCameraDistance(scale)
        backLayout.setCameraDistance(scale)

    }


}
