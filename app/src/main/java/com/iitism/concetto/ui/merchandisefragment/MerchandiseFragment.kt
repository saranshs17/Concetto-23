package com.iitism.concetto.ui.merchandisefragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Snackbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentMerchandiseBinding
import java.lang.Math.abs
import com.google.android.material.snackbar.Snackbar
import com.iitism.concetto.ui.merchandisefragment.retrofit.ApiResponse
import com.iitism.concetto.ui.merchandisefragment.retrofit.DetailsDataModel
import com.iitism.concetto.ui.merchandisefragment.retrofit.NetworkService
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.Calendar.SHORT
import java.util.jar.Manifest
import javax.security.auth.callback.Callback


class MerchandiseFragment : Fragment() {

    companion object {
        fun newInstance() = MerchandiseFragment()
        const val REQUEST_CODE_IMAGE = 101
    }


    var isSizeSelected = 0
    var isImageUploaded = 0
    private var selectedImagebase64: String? = null
    private lateinit var viewModel: MerchandiseViewModel
    private lateinit var dataModel: DetailsDataModel
    private lateinit var binding: FragmentMerchandiseBinding
    private val networkService = NetworkService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMerchandiseBinding.inflate(inflater,container,false)
        val view = binding.root
        val viewPager = binding.viewPagerCorousel

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        val merchandise_images_data = arrayListOf(
            R.drawable.merchandise_1 , R.drawable.merchandise_2,
            R.drawable.merchandise_3, R.drawable.merchandise_4,
            R.drawable.size_chart,R.drawable.merchandise_5,R.drawable.merchandise_6)
        viewPager.adapter = CorouselAdapter(merchandise_images_data)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.setPageTransformer(compositePageTransformer)
        binding.chooseSize.setOnClickListener { showSizeMenu(view)
            isSizeSelected = 1}
        binding.choosePaymentSs.setOnClickListener { opeinImageChooser()
            isImageUploaded=1}

        binding.placeOrderButton.setOnClickListener { placeOrder()}
        return  view
    }

    var selectedSizeIndex = 0;
    var selectedSize : String? = null
    fun showSizeMenu(view: View)
    {
        val t_shirt_size = arrayOf("XS","S","M","L","XL","2XL","3XL")
        selectedSize = t_shirt_size[selectedSizeIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose Size")
            .setSingleChoiceItems(t_shirt_size, selectedSizeIndex){ dialog, which ->
                selectedSizeIndex = which
                selectedSize = t_shirt_size[selectedSizeIndex]
            }
            .setPositiveButton("OK"){dialog,which ->
                showSnackBar("$selectedSize selected")
                //implement here the size part
            }
            .setNeutralButton("Cancel"){dialog,which ->
                Toast.makeText(requireContext(),"Size is required",Toast.LENGTH_LONG).show()
            }
            .show()
    }


    private fun showSnackBar(msg : String)
    {
        Snackbar.make(binding.root,msg,Snackbar.LENGTH_LONG)
    }
    private val READ_EXTERNAL_STORAGE_REQUEST = 123

    private  fun opeinImageChooser()
    {
//
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("text","ButtonClicked")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST
            )

            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                chooseImage()
            } else {
                Toast.makeText(requireContext(), "Permissions not granted by the user A",
                    Toast.LENGTH_SHORT).show()
            }
//            chooseImage()
        } else {
            Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
            chooseImage()
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Asking Permission 1 st Time",Toast.LENGTH_SHORT).show()
            chooseImage()
        } else {
            Toast.makeText(requireContext(), "Permissions not granted by the user B",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseImage()
    {
        Log.i("text","ButtonClicked")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            when(requestCode){
                REQUEST_CODE_IMAGE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val base64Image = encodeImageToBase64(imageBitmap)
                    selectedImagebase64 = base64Image.toString()
                }

            }
        }

    }

    private fun encodeImageToBase64(bitmap: Bitmap): Any {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private  fun placeOrder()
    {
        dataModel = DetailsDataModel(binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString())

        dataModel.name = binding.editName.text.toString()
        dataModel.admno = binding.editAdmNo.text.toString()
        dataModel.email = binding.editEdmail.text.toString()
        dataModel.branch = binding.editBranch.text.toString()
        dataModel.hostel = binding.editHostel.text.toString()
        dataModel.phnno = binding.editPhnNo.text.toString()
        dataModel.roomno = binding.editRoomNo.text.toString()
        dataModel.transactionId = binding.editTransactionId.text.toString()
        dataModel.size = selectedSize.toString()
        dataModel.payementUri = selectedImagebase64.toString()

        var flag = 1
        if(dataModel.name.isEmpty()){
            binding.editName.error = "Name can't be empty"
            Log.d("Field1",dataModel.name)
            flag = 0
        }
        if(dataModel.admno.isEmpty()){
            binding.editAdmNo.error = "Admission no. can't be empty"
            Log.d("Field1",dataModel.admno)
            flag = 0
        }
        if(dataModel.email.isEmpty()){
            binding.editEdmail.error = "Email can't be empty"
            Log.d("Field1",dataModel.email)
            flag = 0
        }
        if(dataModel.branch.isEmpty()){

            binding.editBranch.error = "Branch can't be empty"
            Log.d("Field1",dataModel.branch)
            flag = 0
        }
        if(dataModel.hostel.isEmpty()){

            binding.editHostel.error = "Hostel name can't be empty"
            Log.d("Field1",dataModel.hostel)
            flag = 0
        }
        if(dataModel.phnno.isEmpty()){

            binding.editPhnNo.error = "Phone no. can't be empty"
            Log.d("Field1",dataModel.phnno)
            flag = 0
        }
        if(dataModel.roomno.isEmpty()){

            binding.editRoomNo.error = "Room no. can't be empty"
            Log.d("Field1",dataModel.roomno)
            flag = 0
        }
        if(dataModel.transactionId.isEmpty()){

            binding.editTransactionId.error = "Transaction ID can't be empty"
            Log.d("Field1",dataModel.transactionId)
            flag = 0
        }

        if(isSizeSelected==0){
            flag = 0
            Toast.makeText(context,"Size not Selected!!",Toast.LENGTH_SHORT).show()
        }
        if(selectedImagebase64 == null){
            flag = 0
            Toast.makeText(context,"Image not Uploaded!!",Toast.LENGTH_SHORT).show()
        }

        if(flag == 1 && isSizeSelected==1 && selectedImagebase64!=null){
            Toast.makeText(context,"Placed!!",Toast.LENGTH_SHORT).show()
            val call = networkService.merchandiseApiService.uploadData(dataModel)
            call.enqueue(dataModel)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MerchandiseViewModel::class.java)
    }
}

private fun <T> Call<T>.enqueue(dataModel: DetailsDataModel) {
}
