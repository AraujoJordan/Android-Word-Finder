package com.araujo.jordan.wordfindify

import android.content.Intent
import androidx.test.espresso.IdlingPolicies
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.araujo.jordan.latest.scrollUntilFindText
import com.araujo.jordan.latest.textClick
import com.araujo.jordan.latest.uiDevice
import com.araujo.jordan.wordfindify.models.BoardChararacter
import com.araujo.jordan.wordfindify.views.board.BoardActivity
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@LargeTest
@RunWith(androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner::class)
class BoardInstrumentedTest {

    @get:Rule
    val activityRule =
        object : ActivityTestRule<BoardActivity>(BoardActivity::class.java, false, false) {}

    @Before
    fun registerIdlingResource() {
        IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.SECONDS)
        IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.SECONDS)
    }

    @Test
    fun testMenuAbout() {
        launchApp()
        uiDevice().apply {
            textClick("About")
            assertTrue(scrollUntilFindText("Contact me")?.isEnabled == true)
        }
    }

    private fun launchApp() {
        val i = Intent()

        val board = ArrayList<ArrayList<BoardChararacter>>()
//        board.add(ArrayList(BoardChararacter()))


        i.putExtra(
            "testingBoard", ArrayList<ArrayList<BoardChararacter>>()
        )
        activityRule.launchActivity(i)
    }

//    private fun boardLine(board:ArrayList<BoardChararacter>, vararg char : Char) : ArrayList<BoardChararacter> {
//        val boardLine = board.size
//        val boardColumn = ArrayList<BoardChararacter>()
//    }

}
