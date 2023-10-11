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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.iitism.concetto.R
import com.iitism.concetto.databinding.ActivityMainBinding
import com.iitism.concetto.databinding.ActivityRegisterBinding
import com.iitism.concetto.ui.merchandisefragment.retrofit.ApiResponse
import com.iitism.concetto.ui.registrationEvent.retrofit.ApiService
import com.iitism.concetto.ui.registrationEvent.retrofit.Member
import com.iitism.concetto.ui.registrationEvent.retrofit.RegisterDataModel
import com.iitism.concetto.ui.registrationEvent.retrofit.RetrofitInstance
import com.iitism.concetto.ui.registrationEvent.retrofit.Stage
import retrofit2.Call
import retrofit2.Response
import kotlin.properties.Delegates

class RegisterActivity() : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    private lateinit var teamName: TextView
  //  private lateinit var teamLeader: TextView

    private lateinit var problemStmt: TextView
    private lateinit var botWeight: TextView
    private lateinit var driveLink: TextView
    private lateinit var fieldOfIntrest: TextView
    private lateinit var member1layout: LinearLayout
    private lateinit var member2layout: LinearLayout
    private lateinit var member3layout: LinearLayout
    private lateinit var member4layout: LinearLayout
    private lateinit var member5layout: LinearLayout
    private lateinit var registerBtn: Button
    private lateinit var imageViewRegister: ImageView
    private lateinit var loadingComponent: CardView
    private lateinit var viewModel: RegisterViewModel
    private lateinit var refreshButton: Button
    private lateinit var datamodel : RegisterDataModel
   // private lateinit var admissionNumber : TextView
    private lateinit var eventName : String
    private var  minTeamSize : Int = 0
    var maxTeamSize: Int = 0

    var isMemberSelected = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventName = intent?.getStringExtra("EventName") ?: ""
        teamName = findViewById(R.id.edit_team_name)
       // teamLeader = findViewById(R.id.edit_team_leader)

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
       // admissionNumber = findViewById(R.id.edit_admission)
        loadingComponent.visibility = View.VISIBLE



        val eventID: String = intent?.getStringExtra("id") ?: ""
        val posterUrl: String = intent?.getStringExtra("posterUrl") ?: ""
        minTeamSize = intent?.getIntExtra("min", 1) ?: 1
        maxTeamSize = intent?.getIntExtra("max", 5) ?: 5

       // admissionNumber.visibility = View.VISIBLE

        if (eventID != null) {
            viewModel = ViewModelProvider(this, RegistrationViewModelFactory(this, eventID)).get(
                RegisterViewModel::class.java
            )
            networkCheckAndRun()
        }
        refreshButton.setOnClickListener()
        {
            refreshButton.visibility = View.GONE
            loadingComponent.visibility = View.VISIBLE
            networkCheckAndRun()
        }

        Log.i("URL", posterUrl)
        Log.i("eventId", eventID)
        if (posterUrl != "") {

            Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.concetto_full_logo)
                .centerCrop()
                .into(imageViewRegister)
        }



        binding.chooseMembers.setOnClickListener { showMemberMenu() }
        registerBtn.visibility = View.VISIBLE
        registerBtn.setOnClickListener {
            register()
            Log.i("Btn Pressed","Btn is Pressed")
        }


    }

    var selectedIndex = 0;
    var selectedMember : String? = null
    fun showMemberMenu()
    {

        val memberList = ArrayList<String>()
        memberList.add("Select")

       for (i in minTeamSize..maxTeamSize)
        memberList.add(i.toString())


        val member = memberList.toTypedArray()

        selectedMember = member[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("No of Members")
            .setSingleChoiceItems(member, selectedIndex){ dialog, which ->
                selectedIndex = which
                selectedMember = member[selectedIndex]
            }
            .setPositiveButton("OK"){dialog,which ->
                isMemberSelected = 1
                if(selectedIndex == 0)
                {
                    Toast.makeText(this,"Number of members is required",Toast.LENGTH_SHORT)
                    isMemberSelected = 0;
                }
                if(isMemberSelected == 1) {
                    showSnackBar("$selectedMember members selected")
                    binding.chooseMembers.text = member[selectedIndex]

                    //implement here the size part
                    when (selectedMember?.toInt()) {
                        1 -> {
                            member1layout.visibility = View.VISIBLE
                            member2layout.visibility = View.GONE
                            member3layout.visibility = View.GONE
                            member4layout.visibility = View.GONE
                            member5layout.visibility = View.GONE
                        }

                        2 -> {
                            member1layout.visibility = View.VISIBLE
                            member2layout.visibility = View.VISIBLE
                            member3layout.visibility = View.GONE
                            member4layout.visibility = View.GONE
                            member5layout.visibility = View.GONE
                        }

                        3 -> {
                            member1layout.visibility = View.VISIBLE
                            member2layout.visibility = View.VISIBLE
                            member3layout.visibility = View.VISIBLE
                            member4layout.visibility = View.GONE
                            member5layout.visibility = View.GONE
                        }

                        4 -> {
                            member1layout.visibility = View.VISIBLE
                            member2layout.visibility = View.VISIBLE
                            member3layout.visibility = View.VISIBLE
                            member4layout.visibility = View.VISIBLE
                            member5layout.visibility = View.GONE
                        }

                        5 -> {
                            member1layout.visibility = View.VISIBLE
                            member2layout.visibility = View.VISIBLE
                            member3layout.visibility = View.VISIBLE
                            member4layout.visibility = View.VISIBLE
                            member5layout.visibility = View.VISIBLE
                        }

                    }
                }
            }
            .setNeutralButton("Cancel"){dialog,which ->
                Toast.makeText(this,"Number of members is required",Toast.LENGTH_LONG).show()
            }
            .show()


    }
    private fun showSnackBar(msg : String)
    {
        Snackbar.make(binding.root,msg, Snackbar.LENGTH_SHORT).show()
    }
    private fun networkCheckAndRun() {
        loadingComponent.visibility = View.GONE
        if (viewModel.isNetworkAvailable()) {
            if (viewModel.fetchData()) {
                loadingComponent.visibility = View.GONE
                Log.i("Criteria", viewModel.criterialList.value.toString())
            } else {
//               refreshButton.visibility =View.VISIBLE
            }
        }
    }


    private fun register()
    {

        datamodel = RegisterDataModel("asfnlk","safnla","afnd","afkndk", emptyList<Member>(),"aknfkd", emptyList<Stage>(),"akfnkdf","sfeka",)
        datamodel.botWeight = botWeight.text.toString()
        datamodel.driveLink = driveLink.text.toString()
        datamodel.eventName = eventName
        datamodel.fieldOfInterest = fieldOfIntrest.text.toString()
        datamodel.problemStatement = problemStmt.text.toString()
       // datamodel.admissionNumber = admissionNumber.text.toString()
        datamodel.teamLeader = binding.editTeamLeader.text.toString()
        datamodel.teamName = teamName.text.toString()

        val membersList = mutableListOf<Member>()

            val member1 = Member(binding.editBranch1.text.toString(),binding.editCollege1.text.toString(),binding.editEmail1.text.toString(),binding.editTeamLeader.text.toString(),binding.editPhone1.text.toString(),binding.editYos1.text.toString(),binding.editAdmission1.text.toString())
        val member2 = Member(binding.editBranch2.text.toString(),binding.editCollege2.text.toString(),binding.editEmail2.text.toString(),binding.editName2.text.toString(),binding.editPhone2.text.toString(),binding.editYos2.text.toString(),binding.editAdmission2.text.toString())
        val member3 = Member(binding.editBranch3.text.toString(),binding.editCollege3.text.toString(),binding.editEmail3.text.toString(),binding.editName3.text.toString(),binding.editPhone3.text.toString(),binding.editYos3.text.toString(),binding.editAdmission3.text.toString())
        val member4 = Member(binding.editBranch4.text.toString(),binding.editCollege4.text.toString(),binding.editEmail4.text.toString(),binding.editName4.text.toString(),binding.editPhone4.text.toString(),binding.editYos4.text.toString(),binding.editAdmission4.text.toString())
        val member5 = Member(binding.editBranch5.text.toString(),binding.editCollege5.text.toString(),binding.editEmail5.text.toString(),binding.editName5.text.toString(),binding.editPhone5.text.toString(),binding.editYos5.text.toString(),binding.editAdmission5.text.toString())


when(selectedMember?.toInt())
{
    1->{ membersList.add(member1)
    }
    2 -> {
        membersList.add(member1)
        membersList.add(member2)
    }
    3 ->
    {
        membersList.add(member1)
        membersList.add(member2)
        membersList.add(member3)
    }
    4-> {
        membersList.add(member1)
        membersList.add(member2)
        membersList.add(member3)
        membersList.add(member4)
    }
    5 ->{
        membersList.add(member1)
        membersList.add(member2)
        membersList.add(member3)
        membersList.add(member4)
        membersList.add(member5)
    }
}


        datamodel.member =membersList
        datamodel.stages = emptyList()
        var flag=1;
if(viewModel.criterialList.value?.botWeight == true)
{
    botWeight.visibility = View.VISIBLE
if (botWeight.text.isEmpty()){
    botWeight.error="Empty Fields Not Allowed";
    flag = 0;
}}
        if(viewModel.criterialList.value?.driveLink == true)
        {
            driveLink.visibility = View.VISIBLE
        if(driveLink.text.isEmpty()){
            driveLink.error="Empty Fields Not Allowed";
            flag = 0;
        }}
        if(viewModel.criterialList.value?.fieldOfInterest == true)
        {
            fieldOfIntrest.visibility = View.VISIBLE
        if(fieldOfIntrest.text.isEmpty()){
            fieldOfIntrest.error="Empty Fields Not Allowed";
            flag = 0;
        }}
        if(viewModel.criterialList.value?.botWeight == true)
        {
            problemStmt.visibility = View.VISIBLE

        if(problemStmt.text.isEmpty()){
            problemStmt.error="Empty Fields Not Allowed";
            flag = 0;
        }}
//        if(admissionNumber.text.isEmpty()){
//            admissionNumber.error="Empty Fields Not Allowed";
//            flag = 0;
//        }
//        if(teamLeader.text.isEmpty()){
//            teamLeader.error="Empty Fields Not Allowed";
//            flag = 0;
//        }
        if(teamName.text.isEmpty()){
            teamName.error="Empty Fields Not Allowed";
            flag = 0;
        }

        when(selectedMember?.toInt())
        {
            1 ->{
                if(binding.editBranch1.text.isEmpty()){
                    binding.editBranch1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege1.text.isEmpty()){
                    binding.editCollege1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail1.text.isEmpty()){
                    binding.editEmail1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

                if (binding.editPhone1.text.isEmpty()){
                    binding.editPhone1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos1.text.isEmpty()){
                    binding.editYos1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission1.text.isEmpty())
                {
                    binding.editAdmission1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

            }

            2 ->{if(binding.editBranch1.text.isEmpty()){
                binding.editBranch1.error="Empty Fields Not Allowed";
                flag = 0;
            }
                if (binding.editCollege1.text.isEmpty()){
                    binding.editCollege1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail1.text.isEmpty()){
                    binding.editEmail1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

                if (binding.editPhone1.text.isEmpty()){
                    binding.editPhone1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos1.text.isEmpty()){
                    binding.editYos1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission1.text.isEmpty())
                {
                    binding.editAdmission1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch2.text.isEmpty()){
                    binding.editBranch2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege2.text.isEmpty()){
                    binding.editCollege2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail2.text.isEmpty()){
                    binding.editEmail2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName2.text.isEmpty()){
                    binding.editName2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone2.text.isEmpty()){
                    binding.editPhone2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos2.text.isEmpty()){
                    binding.editYos2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission2.text.isEmpty())
                {
                    binding.editAdmission2.error="Empty Fields Not Allowed";
                    flag = 0;
                }

            }
            3-> {
                if(binding.editBranch1.text.isEmpty()){
                    binding.editBranch1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege1.text.isEmpty()){
                    binding.editCollege1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail1.text.isEmpty()){
                    binding.editEmail1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

                if (binding.editPhone1.text.isEmpty()){
                    binding.editPhone1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos1.text.isEmpty()){
                    binding.editYos1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission1.text.isEmpty())
                {
                    binding.editAdmission1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch2.text.isEmpty()){
                    binding.editBranch2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege2.text.isEmpty()){
                    binding.editCollege2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail2.text.isEmpty()){
                    binding.editEmail2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName2.text.isEmpty()){
                    binding.editName2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone2.text.isEmpty()){
                    binding.editPhone2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos2.text.isEmpty()){
                    binding.editYos2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission2.text.isEmpty())
                {
                    binding.editAdmission2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch3.text.isEmpty()){
                    binding.editBranch3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege3.text.isEmpty()){
                    binding.editCollege3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail3.text.isEmpty()){
                    binding.editEmail3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName3.text.isEmpty()){
                    binding.editName3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone3.text.isEmpty()){
                    binding.editPhone3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos3.text.isEmpty()){
                    binding.editYos3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission3.text.isEmpty())
                {
                    binding.editAdmission3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
            }

            4 -> {
                if(binding.editBranch1.text.isEmpty()){
                    binding.editBranch1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege1.text.isEmpty()){
                    binding.editCollege1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail1.text.isEmpty()){
                    binding.editEmail1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

                if (binding.editPhone1.text.isEmpty()){
                    binding.editPhone1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos1.text.isEmpty()){
                    binding.editYos1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission1.text.isEmpty())
                {
                    binding.editAdmission1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch2.text.isEmpty()){
                    binding.editBranch2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege2.text.isEmpty()){
                    binding.editCollege2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail2.text.isEmpty()){
                    binding.editEmail2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName2.text.isEmpty()){
                    binding.editName2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone2.text.isEmpty()){
                    binding.editPhone2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos2.text.isEmpty()){
                    binding.editYos2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission2.text.isEmpty())
                {
                    binding.editAdmission2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch3.text.isEmpty()){
                    binding.editBranch3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege3.text.isEmpty()){
                    binding.editCollege3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail3.text.isEmpty()){
                    binding.editEmail3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName3.text.isEmpty()){
                    binding.editName3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone3.text.isEmpty()){
                    binding.editPhone3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos3.text.isEmpty()){
                    binding.editYos3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission3.text.isEmpty())
                {
                    binding.editAdmission3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch4.text.isEmpty()){
                    binding.editBranch4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege4.text.isEmpty()){
                    binding.editCollege4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail4.text.isEmpty()){
                    binding.editEmail4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName4.text.isEmpty()){
                    binding.editName4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone4.text.isEmpty()){
                    binding.editPhone4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos4.text.isEmpty()){
                    binding.editYos4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission4.text.isEmpty())
                {
                    binding.editAdmission4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
            }

            5-> {
                if(binding.editBranch1.text.isEmpty()){
                    binding.editBranch1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege1.text.isEmpty()){
                    binding.editCollege1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail1.text.isEmpty()){
                    binding.editEmail1.error="Empty Fields Not Allowed";
                    flag = 0;
                }

                if (binding.editPhone1.text.isEmpty()){
                    binding.editPhone1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos1.text.isEmpty()){
                    binding.editYos1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission1.text.isEmpty())
                {
                    binding.editAdmission1.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch2.text.isEmpty()){
                    binding.editBranch2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege2.text.isEmpty()){
                    binding.editCollege2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail2.text.isEmpty()){
                    binding.editEmail2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName2.text.isEmpty()){
                    binding.editName2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone2.text.isEmpty()){
                    binding.editPhone2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos2.text.isEmpty()){
                    binding.editYos2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission2.text.isEmpty())
                {
                    binding.editAdmission2.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch3.text.isEmpty()){
                    binding.editBranch3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege3.text.isEmpty()){
                    binding.editCollege3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail3.text.isEmpty()){
                    binding.editEmail3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName3.text.isEmpty()){
                    binding.editName3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone3.text.isEmpty()){
                    binding.editPhone3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos3.text.isEmpty()){
                    binding.editYos3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission3.text.isEmpty())
                {
                    binding.editAdmission3.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch4.text.isEmpty()){
                    binding.editBranch4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege4.text.isEmpty()){
                    binding.editCollege4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail4.text.isEmpty()){
                    binding.editEmail4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName4.text.isEmpty()){
                    binding.editName4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone4.text.isEmpty()){
                    binding.editPhone4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos4.text.isEmpty()){
                    binding.editYos4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission4.text.isEmpty())
                {
                    binding.editAdmission4.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if(binding.editBranch5.text.isEmpty()){
                    binding.editBranch5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editCollege5.text.isEmpty()){
                    binding.editCollege5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editEmail5.text.isEmpty()){
                    binding.editEmail5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editName5.text.isEmpty()){
                    binding.editName5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editPhone5.text.isEmpty()){
                    binding.editPhone5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editYos5.text.isEmpty()){
                    binding.editYos5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
                if (binding.editAdmission5.text.isEmpty())
                {
                    binding.editAdmission5.error="Empty Fields Not Allowed";
                    flag = 0;
                }
            }
        }
      if(flag == 1 && (selectedMember?.toInt() ?: 0) >= minTeamSize.toInt()) {
           sendData()
      }
        else
      {
          if ((selectedMember?.toInt() ?: 0) < minTeamSize.toInt())
          {
              Toast.makeText(this,"Select the no of team Members", Toast.LENGTH_SHORT).show()
          }
      }
    }

    fun sendData()
    {

            binding.loadingCard.visibility = View.VISIBLE
            binding.scrollViewMerchandise.visibility = View.INVISIBLE

          Log.i("dataModel",datamodel.toString())
            val call = RetrofitInstance().apiService.uplaodData(datamodel)
            call.enqueue(object : retrofit2.Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                    binding.scrollViewMerchandise.visibility = View.VISIBLE

                        Log.i("Tag", response.toString())

                    Log.i("response", response.body()?.msg.toString())
                        if (response.body() == null) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Something went wrong!" + response.code().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.loadingCard.visibility = View.GONE
                        }
                        else
                            Toast.makeText(
                                this@RegisterActivity,
                                "Member is Successfully Registered!!",
                                Toast.LENGTH_SHORT
                            ).show()

                    clearEditTextFields()
                    binding.loadingCard.visibility = View.GONE

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.i("Tag",t.toString())
                    binding.loadingCard.visibility = View.INVISIBLE
                    binding.scrollViewMerchandise.visibility = View.VISIBLE
                    Toast.makeText(this@RegisterActivity,"Try again !!, It may happen first time",Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun clearEditTextFields() {
        // Clear all EditText fields for members
        binding.editBranch1.text.clear()
        binding.editCollege1.text.clear()
        binding.editEmail1.text.clear()
        binding.editTeamName.text.clear()
        binding.editPhone1.text.clear()
        binding.editYos1.text.clear()
        binding.editAdmission1.text.clear()

        binding.editBranch2.text.clear()
        binding.editCollege2.text.clear()
        binding.editEmail2.text.clear()
        binding.editName2.text.clear()
        binding.editPhone2.text.clear()
        binding.editYos2.text.clear()
        binding.editAdmission2.text.clear()

        binding.editBranch3.text.clear()
        binding.editCollege3.text.clear()
        binding.editEmail3.text.clear()
        binding.editName3.text.clear()
        binding.editPhone3.text.clear()
        binding.editYos3.text.clear()
        binding.editAdmission3.text.clear()

        binding.editBranch4.text.clear()
        binding.editCollege4.text.clear()
        binding.editEmail4.text.clear()
        binding.editName4.text.clear()
        binding.editPhone4.text.clear()
        binding.editYos4.text.clear()
        binding.editAdmission4.text.clear()

        binding.editBranch5.text.clear()
        binding.editCollege5.text.clear()
        binding.editEmail5.text.clear()
        binding.editName5.text.clear()
        binding.editPhone5.text.clear()
        binding.editYos5.text.clear()
        binding.editAdmission5.text.clear()

        binding.editTeamLeader.text.clear()
    }
}


