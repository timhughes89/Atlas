package com.timsimonhughes.atlas.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.timsimonhughes.atlas.R

class OnboardingPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        var pageWidth = page.width
        val pageWidthTimesPosition = pageWidth * position
        val absPosition = Math.abs(position)

        if (position <= -1.0f || position >= 1.0f) {
            // Page is not visible, stop running animations
        } else if (position == 0.0f) {
            // Page is visible reset any animations
        } else {
            // Page 1
            val textViewNewsTitle = page.findViewById<TextView>(R.id.text_view_onboarding_title_1)
            val textViewNewsDescription = page.findViewById<TextView>(R.id.text_view_onboarding_description_1)

            textViewNewsTitle?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }

            textViewNewsDescription?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }

            // Page 2
            val textViewDiscoverTitle = page.findViewById<TextView>(R.id.text_view_onboarding_title_2)
            val textViewDiscoverDescription = page.findViewById<TextView>(R.id.text_view_onboarding_description_2)
            val imageViewDiscover1 = page.findViewById<ImageView>(R.id.image_view_1)
            val imageViewDiscover2 = page.findViewById<ImageView>(R.id.image_view_2)
            val imageViewDiscover3 = page.findViewById<ImageView>(R.id.image_view_3)

            textViewDiscoverTitle?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }

            textViewDiscoverDescription?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }

            imageViewDiscover1?.apply {
                translationX = pageWidthTimesPosition * 1.4f
            }

            imageViewDiscover2?.apply {
                translationX = pageWidthTimesPosition * 1.0f
            }

            imageViewDiscover3?.apply {
                translationX = pageWidthTimesPosition * 0.8f
            }

            // Page 3
            val textViewPhotosTitle = page.findViewById<TextView>(R.id.text_view_onboarding_title_3)
            val textViewPhotosDescription = page.findViewById<TextView>(R.id.text_view_onboarding_description_3)

            textViewPhotosTitle?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }

            textViewPhotosDescription?.apply {
                alpha = 1.0f - absPosition * 2
                translationX = pageWidthTimesPosition * 0.6f
            }
        }
    }
}