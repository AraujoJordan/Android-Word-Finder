/**
 * Designed and developed by Jordan Lira (@araujojordan)
 *
 * Copyright (C) 2020 Jordan Lira de Araujo Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.araujo.jordan.wordfindify.views.board

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.araujo.jordan.wordfindify.R
import com.araujo.jordan.wordfindify.presenter.level.LevelBuilder
import kotlinx.android.synthetic.main.dialog_game_end.view.*

/**
 * Dialog that show the win, lose or error messages
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class WinLoseDialog(act: BoardActivity, victory: Boolean, msg: String? = null) {
    init {
        val dialog = AlertDialog.Builder(act, R.style.RoundedDialog)
        dialog.setCancelable(false)
        val v = act.layoutInflater.inflate(R.layout.dialog_game_end, null)
        dialog.setView(v)
        val dialogComplete = dialog.show()
        v.dialogWinLoseLogo.startAnimation(AnimationUtils.loadAnimation(act, R.anim.winlose_anim))
        v.dialogWinLoseLogo.setImageDrawable(
            AppCompatResources.getDrawable(
                v.context,
                if (victory) R.drawable.ic_win else R.drawable.ic_gameover
            )
        )
        if (msg != null) {
            v.dialogWinloseText.text = msg
        } else
            v.dialogWinloseText.setText(if (victory) R.string.dialogYouWinTitle else R.string.loseTitleDialog)
        v.dialogRightButtonImage.setImageDrawable(
            AppCompatResources.getDrawable(
                v.context,
                if (victory) R.drawable.ic_play_arrow_black_24dp else R.drawable.ic_levels
            )
        )
        val level = act.intent.getIntExtra("level", 1)
        if (victory && level == LevelBuilder().getLevels().size) v.dialogRightButton.visibility =
            View.GONE
        v.dialogRightButton.setOnClickListener {
            act.finish()
            if (victory) {
                val nextLevel = Intent(act, BoardActivity::class.java)
                nextLevel.putExtra("difficulty", act.intent.getStringExtra("difficulty"))
                nextLevel.putExtra("level", level)
                dialogComplete.dismiss()
            }
        }
        v.dialogLeftButton.setOnClickListener {
            act.reset()
            dialogComplete.dismiss()
        }
    }
}