package com.iitism.hackfestapp.ui.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iitism.hackfestapp.R
import com.iitism.hackfestapp.databinding.FragmentContactUsBinding

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
                    Uri.parse("https://www.facebook.com/hackfestiitism?mibextid=ZbWKwL")))
            }
            R.id.instagram -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/hackfestiitism?igshid=YmMyMTA2M2Y=")))
            }
            R.id.linkedin -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/company/hackfest-iit-ism-dhanbad/")))
            }
            R.id.number -> {
                startActivity(Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:+91 98718 42597")))
            }
            R.id.mail -> {
                startActivity(Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", "hackfest@iitism.ac.in", null)))
            }
            R.id.location -> {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=loc:23.81124781385419, 86.44006961857158 ( NVCTI )")))

            }
        }
    }
}