package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
		
		binding.fragmentLoginLoginButton.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
		}
		binding.fragmentLoginSignupButton.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
		}
	}
}