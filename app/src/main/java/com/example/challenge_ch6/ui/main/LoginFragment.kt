package com.example.challenge_ch6.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge_ch6.databinding.FragmentLoginBinding
import com.example.challenge_ch6.ui.state.UserState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterHere.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.editText?.text.toString()
            val password = binding.editTextPassword.editText?.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{

                viewModel.loginUser(username, password)

                viewModel.userState.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is UserState.Success -> {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())
                        }
                        is UserState.Error -> {
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                            Log.e("LoginFragment", state.error)
                        }
                        is UserState.Loading -> {
                            //do nothing
                        }
                    }
                }
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.buttonLogin.setOnClickListener {
//            val username = binding.editTextUsername.editText?.text.toString()
//            val password = binding.editTextPassword.editText?.text.toString()
//
//            viewModel.loginUser(username, password)
//            findNavController().navigateUp()
//
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}