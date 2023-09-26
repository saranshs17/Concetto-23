package com.iitism.concetto.ui.merchandisefragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iitism.concetto.R
import com.iitism.concetto.databinding.FragmentMerchandiseBinding
import com.google.android.material.snackbar.Snackbar
import com.iitism.concetto.ui.merchandisefragment.retrofit.ApiResponse
import com.iitism.concetto.ui.merchandisefragment.retrofit.DetailsDataModel
import com.iitism.concetto.ui.merchandisefragment.retrofit.NetworkService
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.math.abs


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


//          adapter = ImageSliderAdapter(requireContext(),merchandise_images_data)
//         viewPager.adapter = adapter
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
                        val imageBitmap = readImageFromGallery(requireContext(), imageUri)
                        if (imageBitmap != null) {
                            selectedImagebase64 = encodeImageToBase64(imageBitmap).toString()
                            Log.i("URL",selectedImagebase64.toString())
                        } else {
                            // Handle the case where imageBitmap is null (e.g., failed to decode the image)
                        }
                    }
                }
            }
        }
    }

    // Function to read an image from gallery and return a Bitmap
    private fun readImageFromGallery(context: Context, imageUri: Uri?): Bitmap? {
        val contentResolver: ContentResolver = context.contentResolver
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
//        Log.i("input",dataModel.name)
        dataModel.admissionNumber = binding.editAdmNo.text.toString()
        dataModel.branch = binding.editBranch.text.toString()
        dataModel.hostel = binding.editHostel.text.toString()
        dataModel. mobileNumber = binding.editPhnNo.text.toString()
        dataModel.roomNumber = binding.editRoomNo.text.toString()
        dataModel.transactionID = binding.editTransactionId.text.toString()
        dataModel.tshirtSize = selectedSize.toString()
        dataModel.imageURL = selectedImagebase64.toString()

        var flag = 1
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
//        if(dataModel.email.isEmpty()){
//            binding.editEdmail.error = "Email can't be empty"
//            Log.d("Field1",dataModel.email)
//            flag = 0
//        }
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
        if(selectedImagebase64 == null){
            flag = 0
            Toast.makeText(context,"Image not Uploaded!!",Toast.LENGTH_SHORT).show()
        }

        if(flag == 1 && isSizeSelected==1 && selectedImagebase64!=null){
            binding.loadingCard.visibility = View.VISIBLE
            binding.scrollViewMerchandise.visibility = View.INVISIBLE
            val call = networkService.merchandiseApiService.uploadData(dataModel)
            call.enqueue(object : retrofit2.Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    Log.i("Tag", response.toString())
                    binding.loadingCard.visibility = View.INVISIBLE
                    binding.scrollViewMerchandise.visibility = View.VISIBLE
//                    if(response.body() == null) Toast.makeText(context,"Something went wrong!",Toast.LENGTH_SHORT).show()
//                    else
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


