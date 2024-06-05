package com.example.challenge_ch6.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challenge_ch6.databinding.FragmentUserBinding
import com.example.challenge_ch6.ui.state.UserState
import com.example.challenge_ch6.ui.viewmodel.BlurViewModel
import com.example.challenge_ch6.ui.viewmodel.BlurViewModelFactory
import com.example.challenge_ch6.ui.viewmodel.UserViewModel
import com.example.common.Constant
import com.example.common.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private val blurViewModel: BlurViewModel by viewModels { BlurViewModelFactory(requireActivity().application) }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            userViewModel.updateUserImage(it, requireActivity().contentResolver)
            blurViewModel.setImageUri(it) // Set the imageUri in the BlurViewModel
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            userViewModel.logoutUser()
            findNavController().navigate(UserFragmentDirections.actionUserFragmentToLoginFragment())
        }

        setObserver()



        binding.imgProfile.setOnClickListener {
            galleryLauncher.launch("image/*")
            val imageView: ImageView = it as ImageView
            val uri = getImageUri(requireContext(), imageView)
            if (uri != null) {
                blurViewModel.setImageUri(uri)
            }
        }


        binding.btnApplyBlur.setOnClickListener {
            val uri = blurViewModel.imageUri
            if (uri != null) {
                blurViewModel.applyBlur(3) // Apply blur with level 3
                blurViewModel.outputWorkInfos.observe(viewLifecycleOwner) { workInfos ->
                    val workInfo = workInfos.firstOrNull()
                    if (workInfo != null && workInfo.state.isFinished) {
                        val outputImageUri = workInfo.outputData.getString(Constant.KEY_IMAGE_URI)
                        if (!outputImageUri.isNullOrEmpty()) {
                            Glide.with(this@UserFragment)
                                .load(outputImageUri)
                                .into(binding.imgProfile)
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val username = binding.editTextUsername.editText?.text.toString()
            val password = binding.editTextPassword.editText?.text.toString()
            val konfirmPassword = binding.editTextPasswordConfirm.editText?.text.toString()
            val email = binding.editTextEmail.editText?.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                if(password == konfirmPassword){
                    userViewModel.updateUser(username, password, email)
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setObserver(){

        userViewModel.getUserDetail(SharedPreferences.username)

        userViewModel.userImage.observe(viewLifecycleOwner) { bitmap ->
            Glide.with(this@UserFragment)
                .load(bitmap)
                .into(binding.imgProfile)
        }

        userViewModel.userState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserState.Success -> {
                    val user = state.user
                    binding.editTextUsername.editText?.setText(user.userName)
                    binding.editTextEmail.editText?.setText(user.userEmail)
                }
                is UserState.Error -> {
                    // Handle error state
                }
                is UserState.Loading -> {
                    // Handle loading state
                }
            }
        }

    }


    private fun getImageUri(context: Context, imageView: ImageView): Uri? {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val file = File(context.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        return Uri.fromFile(file)
    }

}