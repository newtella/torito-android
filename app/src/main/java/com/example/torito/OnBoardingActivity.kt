package com.example.torito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.torito.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : AppCompatActivity() {

    private val introSlideAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "Busca tu Destino",
                "Busca y selecciona el area o lugar al que te diriges.",
                R.drawable.art_smartphone
            ),
            IntroSlide(
                "Verificacion de Carrera",
                "Ve los datos del Tuk tuk y la persona que te llevara a tu destino, Genial no?",
                R.drawable.art_profile
            ),
            IntroSlide(
                "Califica tu carrera",
                "Califica la carrera y ayudanos a mejorar el servicio. De lujo!",
                R.drawable.art_taxirate
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarding)
        introSliderViewPager.adapter = introSlideAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener {
            if( introSliderViewPager.currentItem + 1 < introSlideAdapter.itemCount){
                introSliderViewPager.currentItem += 1
            }else {
                Intent( applicationContext, LoginActivity::class.java ).also {
                    startActivity(it)
                    finish()
                }
            }
        }
        textSkip.setOnClickListener {
            finishOnBoarding()
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index)
            {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun finishOnBoarding() {
        val preferences =
            getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        preferences.edit()
            .putBoolean("onBoarding_complete", true).apply()

        val main = Intent(this, LoginActivity::class.java)
        startActivity(main)
        finish()
    }

}