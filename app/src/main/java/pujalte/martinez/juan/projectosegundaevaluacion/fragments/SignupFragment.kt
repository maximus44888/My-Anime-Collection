package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.app.DatePickerDialog
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentSignupBinding
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class SignupFragment : Fragment() {
	private lateinit var binding: FragmentSignupBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentSignupBinding.inflate(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		initialize(savedInstanceState)
	}
	
	private fun initialize(savedInstanceState: Bundle?) {
		val viewModel = ViewModelProvider(
			requireActivity(),
			LoginViewModel.Factory(
				binding.userInput.text.run { if (isNullOrBlank()) "" else toString() },
				binding.passwordInput.text.run { if (isNullOrBlank()) "" else toString() }
			)
		)[LoginViewModel::class.java]
		
		binding.userInput.setText(viewModel.user.value)
		binding.passwordInput.setText(viewModel.password.value)
		
		binding.userInputLayout.editText?.doAfterTextChanged {
			viewModel.setUser(it.toString())
		}
		
		binding.passwordInputLayout.editText?.doAfterTextChanged {
			viewModel.setPassword(it.toString())
		}
		
		viewModel.isValid.observe(viewLifecycleOwner) {
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
		
		binding.loginButton.setOnClickListener {
			findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
		}
		
		binding.signupButton.setOnClickListener {
			FirebaseAuth.getInstance()
				.createUserWithEmailAndPassword(
					binding.userInput.text.toString(),
					binding.passwordInput.text.toString()
				).addOnSuccessListener {
					Firebase.firestore.collection("usuarios").add(
						hashMapOf(
							"email" to binding.userInput.text.toString(),
							"favorites" to listOf<Any>()
						)
					).addOnSuccessListener {
						findNavController().navigate(R.id.action_signupFragment_to_scaffoldFragment)
					}.addOnFailureListener {
						Snackbar.make(
							binding.root,
							it.message ?: "Error al crear usuario",
							Snackbar.LENGTH_SHORT
						).show()
						FirebaseAuth.getInstance().currentUser?.delete()
					}
				}.addOnFailureListener {
					Snackbar.make(
						binding.root,
						it.message ?: "Error al crear usuario",
						Snackbar.LENGTH_SHORT
					).show()
				}
		}
		
		binding.birthdayButton.setOnClickListener {
			DatePickerDialog(
				requireContext(),
				{ _, year, month, day ->
					Calendar.getInstance().apply {
						set(year, month, day)
						viewModel.setBirthday(this)
					}
				},
				Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
			).show()
		}
		
		viewModel.birthday.observe(viewLifecycleOwner) {
			binding.birthdayButton.text = SimpleDateFormat.getDateInstance().format(it.time)
		}
		
		setupButtonSnackbar(binding.googleLoginButton)
		setupButtonSnackbar(binding.facebookLoginButton)
	}
}