package com.vango.presentation.accessacount
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.vango.R
import androidx.navigation.fragment.findNavController

class AccessAccountFragment : Fragment(R.layout.fragment_access_account) {

    companion object {
        fun newInstance() = AccessAccountFragment()
    }

    private val viewModel: AccessAccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el botón para navegar al LoginFragment
        view.findViewById<MaterialButton>(R.id.bt_access_account_login).setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        // Configura el botón para navegar al SignUpFragment
        view.findViewById<MaterialButton>(R.id.bt_access_account_signup).setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }
}