package com.iitism.concetto.ui.merchandisefragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentMerchandiseBinding
import com.iitism.concetto.ui.merchandisefragment.retrofit.ApiResponse
import com.iitism.concetto.ui.merchandisefragment.retrofit.DetailsDataModel
import com.iitism.concetto.ui.merchandisefragment.retrofit.NetworkService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.util.UUID
import kotlin.math.abs


class MerchandiseFragment : Fragment() {

    companion object {
        fun newInstance() = MerchandiseFragment()
        const val REQUEST_CODE_IMAGE = 101
    }


    var isSizeSelected = 0
    var isImageUploaded = 0
    private var selectedImageUri: Uri? = null
    private lateinit var viewModel: MerchandiseViewModel
    private lateinit var dataModel: DetailsDataModel
    private lateinit var binding: FragmentMerchandiseBinding
    private lateinit var viewPager: ViewPager2
//    private lateinit var adapter: ImageSliderAdapter
    private val networkService = NetworkService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMerchandiseBinding.inflate(inflater,container,false)
        val view = binding.root
         viewPager = binding.viewPagerCorousel

//        viewPager.apply {
//            clipChildren = false  // No clipping the left and right items
//            clipToPadding = false  // Show the viewpager in full width without clipping the padding
//            offscreenPageLimit = 3  // Render the left and right items
//            (getChildAt(0) as RecyclerView).overScrollMode =
//                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
//        }

        val merchandise_images_data = arrayOf(
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

        addDotsIndicator(merchandise_images_data.size)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })

        binding.chooseSize.setOnClickListener { showSizeMenu(view)
        isSizeSelected = 1}
        binding.choosePaymentSs.setOnClickListener { selectImage()
            isImageUploaded=1}

        binding.placeOrderButton.setOnClickListener { placeOrder()}
        return  view
    }

    private fun addDotsIndicator(size: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(requireContext())
            dots[i]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.dotLayout.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_active))
    }

    private fun updateDots(position: Int) {
        val childCount = binding.dotLayout.childCount
        for (i in 0 until childCount) {
            val dot = binding.dotLayout.getChildAt(i) as ImageView
            val drawableId =
                if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive
            dot.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawableId))
        }
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

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    Log.i("Tag", "Image Selected")
                    // Check if data is not null and contains the image URI
                    if (data != null && data.data != null) {
                        val imageUri = data.data
                        selectedImageUri = imageUri

                        val imageBase64 = MyFileHandler(requireContext()).handleFile(selectedImageUri!!)
                        var imageName = MyFileHandler(requireContext()).getFileName(selectedImageUri!!)
                        Log.i("image",imageBase64.toString())

                        binding.choosePaymentSs.text = imageName
                    }
                }
            }
        }
    }
    class MyFileHandler(private val context: Context) {
        fun handleFile(imageUri: Uri): String?{
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val imageBytes = inputStream?.readBytes()
            inputStream?.close()

            if (imageBytes != null) {
                val base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                Log.i("Base64Image", base64Image)
                return base64Image
            }
            return ""
        }
        val resolver = context.contentResolver

       fun getFileName(uri: Uri): String {
            val returnCursor: Cursor = resolver.query(uri, null, null, null, null)!!
            val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val name: String = returnCursor.getString(nameIndex)
            returnCursor.close()
            return name
        }

        fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {
            var filePath: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = context.contentResolver.query(contentUri, projection, null, null, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }

            return filePath
        }


    }

    fun generateRandomId(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }


    private  fun placeOrder()
    {
       dataModel = DetailsDataModel(generateRandomId(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString(),binding.editName.text.toString())

        dataModel.name = binding.editName.text.toString()
        dataModel.admissionNumber = binding.editAdmNo.text.toString()
        dataModel.branch = binding.editBranch.text.toString()
        dataModel.hostel = binding.editHostel.text.toString()
        dataModel. mobileNumber = binding.editPhnNo.text.toString()
        dataModel.roomNumber = binding.editRoomNo.text.toString()
        dataModel.transactionID = binding.editTransactionId.text.toString()
        dataModel.tshirtSize = selectedSize.toString()
        dataModel.email = binding.editEdmail.text.toString()

        var fl = 0;
        var st : String = ""
        for (char in dataModel.email)
        {
             if(fl == 1)
             {
                 st += char
             }
            if(char == '@')
                fl = 1;
        }


        var flag = 1

        if(st == "iitism.ac.in")
        {
            binding.editEdmail.error = "Use personal email id"
            Log.d("Field1",binding.editEdmail.text.toString())
            flag = 0
        }
        if(dataModel.name.isEmpty()){
            binding.editName.error = "Name can't be empty"
            Log.d("Field1",binding.editName.text.toString())
            flag = 0
        }
        if(dataModel.admissionNumber.isEmpty()){
            binding.editAdmNo.error = "Admission no. can't be empty"
            Log.d("Field1",dataModel.admissionNumber)
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
        if(dataModel.mobileNumber.isEmpty()){

            binding.editPhnNo.error = "Phone no. can't be empty"
            Log.d("Field1",dataModel.mobileNumber)
            flag = 0
        }
        if(dataModel.roomNumber.isEmpty()){

            binding.editRoomNo.error = "Room no. can't be empty"
            Log.d("Field1",dataModel.roomNumber)
            flag = 0
        }
        if(dataModel.transactionID.isEmpty()){

            binding.editTransactionId.error = "Transaction ID can't be empty"
            Log.d("Field1",dataModel.transactionID)
            flag = 0
        }

        if(isSizeSelected==0){
            flag = 0
            Toast.makeText(context,"Size not Selected!!",Toast.LENGTH_SHORT).show()
        }
        if(selectedImageUri == null){
            flag = 0
            Toast.makeText(context,"Image not Uploaded!!",Toast.LENGTH_SHORT).show()
        }

        if(flag == 1 && isSizeSelected==1 && selectedImageUri !=null){
            binding.loadingCard.visibility = View.VISIBLE
            binding.scrollViewMerchandise.visibility = View.INVISIBLE



            //api hit
            val orderID = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.orderID)
            val name = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.name)
            val admissionNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.admissionNumber)
            val mobileNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.mobileNumber)
            val branch = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.branch)
            val tshirtSize = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.tshirtSize)
            val transactionID = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.transactionID)
            val hostel = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.hostel)
            val roomNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.roomNumber)
            val email  = RequestBody.create("text/plain".toMediaTypeOrNull(), dataModel.email)

            Log.i("ImageUri",selectedImageUri.toString())

            var imageFile : File = File(MyFileHandler(requireContext()).getFilePathFromContentUri(requireContext(),selectedImageUri!!))
//
            Log.i("Data",dataModel.toString())
            Log.i("file",imageFile.toString())


            val fileRequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), imageFile)
            val filePart = MultipartBody.Part.createFormData("image", imageFile.name, fileRequestBody)
            val call = networkService.merchandiseApiService.uploadData(orderID,name,admissionNumber,mobileNumber,branch,tshirtSize,transactionID,hostel,roomNumber,email,filePart)
            call.enqueue(object : retrofit2.Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    Log.i("Tag", response.toString())
                    binding.loadingCard.visibility = View.INVISIBLE
                    binding.scrollViewMerchandise.visibility = View.VISIBLE

                    Log.i("response",response.body().toString())
                    if(response.body() == null) Toast.makeText(context,"Something went wrong!",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context,"Order is succesfully placed!!",Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                   Log.i("Tag",t.toString())
                    binding.loadingCard.visibility = View.INVISIBLE
                    binding.scrollViewMerchandise.visibility = View.VISIBLE
                    Toast.makeText(context,"Connection failed.",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MerchandiseViewModel::class.java)
    }
}


