package com.iitism.concetto.ui.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = ContactUsFragment()
    }

    private lateinit var binding : FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactUsBinding.inflate(inflater)

        binding.number.setOnClickListener(this)
        binding.mail.setOnClickListener(this)
        binding.location.setOnClickListener(this)
        binding.instagram.setOnClickListener(this)
        binding.linkedin.setOnClickListener(this)
        binding.facebook.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.facebook -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/Concettoiitdhanbad/")))
            }
            R.id.instagram -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/concetto.iitism/?hl=en")))
            }
            R.id.linkedin -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/company/concetto-iitism-dhanbad/?originalSubdomain=in")))
            }
            R.id.number -> {
                startActivity(Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:+91 79998 19086")))
            }
            R.id.mail -> {
                startActivity(Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", "concetto@iitism.ac.in", null)))
            }
            R.id.location -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/contrib/114792556265173640054/photos/@23.8144169,86.441249,17z/data=!3m1!4b1!4m3!8m2!3m1!1e1?entry=ttu")))

            }
        }
    }
}