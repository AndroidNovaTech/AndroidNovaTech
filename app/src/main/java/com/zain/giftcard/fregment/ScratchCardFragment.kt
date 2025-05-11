package com.zain.giftcard.fregment

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresPermission
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.zain.giftcard.Adapter.ScratchCardAdapter
import com.zain.giftcard.R
import com.zain.giftcard.models.ScratchCardModel
import com.zain.giftcard.models.ScratchView


class ScratchCardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val cardList = listOf(
        ScratchCardModel("₹100 Cashback!", R.drawable.bg4),
        ScratchCardModel("₹50 off!", R.drawable.bg3),
        ScratchCardModel("Better Luck Next Time", R.drawable.bg)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_scratch_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = ScratchCardAdapter(cardList) { selectedCard ->
            showScratchDialog(selectedCard)
        }
    }

    private fun showScratchDialog(card: ScratchCardModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_scratch, null)
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(dialogView)

        val rewardImage = dialogView.findViewById<ImageView>(R.id.dialogCardImage)
        val scratchView = dialogView.findViewById<ScratchView>(R.id.dialogScratchView)

        // कार्ड का डेटा लोड करें
        rewardImage.setImageResource(card.imageRes)

        // ScratchView Listener सेट करें
        scratchView.setRevealListener { percent ->
            if (percent > 0.8f) {
                scratchView.reveal()
                vibratePhone()

                val confettiView = dialogView.findViewById<LottieAnimationView>(R.id.confettiView)
                confettiView.visibility = View.VISIBLE
                confettiView.playAnimation()
            }
        }

        dialogView.setOnClickListener {
            dialog.dismiss()  // डायलॉग के बाहर क्लिक करने पर डायलॉग बंद हो जाएगा
        }

        dialog.show()
    }


    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun vibratePhone() {
        val vibrator = requireContext().getSystemService(Vibrator::class.java)
        val effect = VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator?.vibrate(effect)
    }
}
