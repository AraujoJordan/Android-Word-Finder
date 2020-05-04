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