package com.araujo.jordan.wordfindify.views.mainMenu

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.utils.hideSystemUI
import com.araujo.jordan.wordfindify.views.mainMenu.fragments.MainMenuFragment
import kotlinx.android.synthetic.main.activity_main_menu.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class MainMenuActivity : AppCompatActivity() {

    var restartAnim: Handler? = null
    var restartRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.menuFragmentContainer,
                MainMenuFragment()
            )
            .commit()


        hideSystemUI(window)
    }

    override fun onPause() {
        super.onPause()
        restartRunnable?.let { restartAnim?.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
        restartRunnable = Runnable {

            mainMenuKonfetti.build()
                .addColors(
                    Color.YELLOW,
                    Color.GREEN,
                    Color.MAGENTA,
                    Color.BLUE
                )
                .setDirection(0.0, 359.0)
                .setSpeed(2f, 18f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2100L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12, 5f))
                .setPosition(-50f, mainMenuKonfetti.width + 50f, -50f, -50f)
                .streamFor(150, 10000L)
            restartRunnable?.let { restartAnim?.postDelayed(it, 10000) }
        }
        restartAnim = Handler()
        restartAnim?.post(restartRunnable)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideSystemUI(window)
    }

    fun replaceFragment(view: View, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addSharedElement(view, view.transitionName)
            .replace(R.id.menuFragmentContainer, fragment)
            .addToBackStack(view.transitionName)
            .commit()
    }

}
