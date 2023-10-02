package com.iitism.concetto.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.iitism.concetto.R
import com.iitism.concetto.ui.singleevent.SingleEventModel
import com.iitism.concetto.ui.singleevent.SingleEventModelItem

class RegisterActivity : AppCompatActivity() {

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
}