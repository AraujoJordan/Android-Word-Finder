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

package com.araujo.jordan.wordfindify.views.mainMenu.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.araujo.jordan.wordfindify.BuildConfig
import com.araujo.jordan.wordfindify.R
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_about_me.*

/**
 * Fragment that show the about menu in the MainMenuActivity
 * @author Jordan L. Araujo Jr. (araujojordan)
 */
class MenuAboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, bundle: Bundle?) =
        inflater.inflate(R.layout.fragment_about_me, view, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutGoBackButton.setOnClickListener { activity?.onBackPressed() }
        aboutGameVersion.text = getString(R.string.aboutGameVersion, BuildConfig.VERSION_NAME)
        aboutContactMe.setOnClickListener { sendEmailWithFallback() }
        aboutVisitMe.setOnClickListener { openLink(getString(R.string.devWebsite)) }
        aboutCheckTheCode?.setOnClickListener { openLink(getString(R.string.projectLink)) }
    }

    private fun openLink(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))


    private fun sendEmailWithFallback() {
        val mailto = "mailto:${getString(R.string.devEmail)}" +
                "?cc= &subject=${Uri.encode(getString(R.string.emailTitle))}" +
                "&body=${Uri.encode(getString(R.string.emailMessage))}"
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)

        try {
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.devEmail)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailTitle))
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailMessage))

            try {
                startActivity(
                    Intent.createChooser(
                        intent,
                        getString(R.string.aboutMeSendEmailDialog)
                    )
                )
            } catch (err: Exception) {
                Log.e("ContactUs", "Couldn't send email")
            }
        }
    }

}