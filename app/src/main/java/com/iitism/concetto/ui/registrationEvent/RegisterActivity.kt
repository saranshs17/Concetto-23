package com.iitism.concetto.ui.registrationEvent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.iitism.concetto.R

class RegisterActivity(
    val eventID: String,
    val posterUrl: String
) : AppCompatActivity() {

    private lateinit var teamName : TextView
    private lateinit var teamLeader : TextView
    private lateinit var noOfMembers : TextView
    private lateinit var problemStmt : TextView
    private lateinit var botWeight : TextView
    private lateinit var driveLink : TextView
    private lateinit var fieldOfIntrest : TextView
    private lateinit var member1layout : LinearLayout
    private lateinit var member2layout : LinearLayout
    private lateinit var member3layout : LinearLayout
    private lateinit var member4layout : LinearLayout
    private lateinit var member5layout : LinearLayout
    private lateinit var registerBtn : Button
    private lateinit var imageViewRegister: ImageView
    private lateinit var loadingComponent: CardView
    private lateinit var viewModel : RegisterViewModel
    private lateinit var refreshButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        teamName = findViewById(R.id.edit_team_name)
        teamLeader = findViewById(R.id.edit_team_leader)
        noOfMembers = findViewById(R.id.edit_noOfMembers)
        problemStmt = findViewById(R.id.edit_PS)
        botWeight = findViewById(R.id.edit_botwt)
        driveLink = findViewById(R.id.edit_DriveLink)
        botWeight = findViewById(R.id.edit_botwt)
        fieldOfIntrest = findViewById(R.id.edit_Foi)
        member1layout = findViewById(R.id.member1)
        member2layout = findViewById(R.id.member2)
        member3layout = findViewById(R.id.member3)
        member4layout = findViewById(R.id.member4)
        member5layout = findViewById(R.id.member5)
        registerBtn = findViewById(R.id.registerbtn)
        imageViewRegister = findViewById(R.id.ivEvent)
        loadingComponent = findViewById(R.id.loading_card)
        refreshButton = findViewById(R.id.retry_button_register_activity)
        loadingComponent.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this,RegistrationViewModelFactory(this,eventID)).get(RegisterViewModel::class.java)

        networkCheckAndRun()
        refreshButton.setOnClickListener()
        {
            refreshButton.visibility = View.GONE
            networkCheckAndRun()
        }

        if(posterUrl != null)
        {
            Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.concetto_full_logo)
                .centerCrop()
                .into(imageViewRegister)
        }

       val minTeamSize :Int = intent.getIntExtra("min",1)
        val maxTeamSize : Int = intent.getIntExtra("max",5)

        val memberNumber : Int = noOfMembers.text.toString().toInt()

        if (memberNumber< minTeamSize && memberNumber > maxTeamSize)
        {
            if (memberNumber< minTeamSize)
            Toast.makeText(this,"Min Team Size is ${minTeamSize}",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Max Team Size is ${maxTeamSize}",Toast.LENGTH_SHORT).show()
        }
        else {
            when (memberNumber) {
                1 -> member1layout.visibility = View.VISIBLE
                2 -> {
                    member1layout.visibility = View.VISIBLE
                    member2layout.visibility = View.VISIBLE
                }

                3 -> {
                    member1layout.visibility = View.VISIBLE
                    member2layout.visibility = View.VISIBLE
                    member3layout.visibility = View.VISIBLE
                }

                4 -> {
                    member1layout.visibility = View.VISIBLE
                    member2layout.visibility = View.VISIBLE
                    member3layout.visibility = View.VISIBLE
                    member4layout.visibility = View.VISIBLE
                }

                5 -> {
                    member1layout.visibility = View.VISIBLE
                    member2layout.visibility = View.VISIBLE
                    member3layout.visibility = View.VISIBLE
                    member4layout.visibility = View.VISIBLE
                    member5layout.visibility = View.VISIBLE
                }

            }
            registerBtn.visibility = View.VISIBLE
        }

    }

    private fun networkCheckAndRun() {
       if(viewModel.isNetworkAvailable())
       {
           if(viewModel.fetchData())
           {
               loadingComponent.visibility = View.GONE
               Log.i("Criteria",viewModel.criterialList.value.toString())
           }
           else
           {
               refreshButton.visibility =View.VISIBLE
           }
       }
        else
            refreshButton.visibility = View.VISIBLE
    }
}