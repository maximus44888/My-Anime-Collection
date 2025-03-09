package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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
		fun updateLoginSignupIsEnabled() {
			val isEnabled =
				!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text.isNullOrBlank()
			binding.loginButton.isEnabled = isEnabled
			binding.signupButton.isEnabled = isEnabled
		}
		
		fun setupTextInputLayoutError(
			textInputLayout: TextInputLayout, error: String = getString(R.string.cant_be_empty),
		) {
			textInputLayout.editText?.doAfterTextChanged {
				if (it.isNullOrBlank()) {
					textInputLayout.error = error
					textInputLayout.isErrorEnabled = true
					updateLoginSignupIsEnabled()
				} else {
					textInputLayout.error = null
					textInputLayout.isErrorEnabled = false
					updateLoginSignupIsEnabled()
				}
			}
		}
		
		setupTextInputLayoutError(binding.userInputLayout)
		setupTextInputLayoutError(binding.passwordInputLayout)
		
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