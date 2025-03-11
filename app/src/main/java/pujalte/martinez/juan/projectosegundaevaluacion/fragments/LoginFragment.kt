package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.LoginViewModel
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
	private lateinit var binding: FragmentLoginBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentLoginBinding.inflate(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		initialize(savedInstanceState)
	}
	
	private fun initialize(savedInstanceState: Bundle?) {
		val viewModel = ViewModelProvider(
			this,
			LoginViewModel.Factory(
				binding.userInputLayout.editText?.text.toString(),
				binding.passwordInputLayout.editText?.text.toString()
			)
		)[LoginViewModel::class.java]
		
		binding.userInputLayout.editText?.doAfterTextChanged {
			viewModel.setUser(it.toString())
		}
		
		binding.passwordInputLayout.editText?.doAfterTextChanged {
			viewModel.setPassword(it.toString())
		}
		
		viewModel.isValid.observe(viewLifecycleOwner) {
			binding.loginButton.isEnabled = it
			binding.signupButton.isEnabled = it
		}
		
		viewModel.isValidUser.observe(viewLifecycleOwner) {
			binding.userInputLayout.error =
				if (it) null else getString(R.string.cant_be_empty)
			binding.userInputLayout.isErrorEnabled = !it
		}
		
		viewModel.isValidPassword.observe(viewLifecycleOwner) {
			binding.passwordInputLayout.error =
				if (it) null else getString(R.string.cant_be_empty)
			binding.passwordInputLayout.isErrorEnabled = !it
		}
		
		fun setupButtonSnackbar(
			button: Button,
			snackbarText: CharSequence = button.text,
			duration: Int = Snackbar.LENGTH_SHORT,
			actionText: CharSequence? = null,
			listener: View.OnClickListener = View.OnClickListener {},
		) {
			button.setOnClickListener {
				Snackbar.make(binding.root, snackbarText, duration).setAction(actionText, listener)
					.show()
			}
		}
		
		binding.loginButton.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment) }
		binding.signupButton.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_signupFragment) }

//		setupButtonSnackbar(binding.loginButton)
//		setupButtonSnackbar(binding.signupButton)
		setupButtonSnackbar(binding.forgotPasswordButton)
		setupButtonSnackbar(binding.googleLoginButton)
		setupButtonSnackbar(binding.facebookLoginButton)
	}
	
}