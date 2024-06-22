package com.example.notzzapp.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.BuildConfig
import com.example.notzzapp.R
import com.example.notzzapp.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var _binding: FragmentAboutBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        appVersion.text = getString(
            R.string.text_app_version,

        )

        license.setOnClickListener {
            launchBrowser(REPO_LICENSE)
        }

        visitURL.setOnClickListener {
            launchBrowser(REPO_URL)
        }
    }

    private fun launchBrowser(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
        startActivity(it)
    }

    companion object {
        const val REPO_URL = "https://github.com/TheCodeMonks/Notzz-App"
        const val REPO_LICENSE = "https://github.com/TheCodeMonks/Notzz-App/blob/master/LICENSE"
    }
}
