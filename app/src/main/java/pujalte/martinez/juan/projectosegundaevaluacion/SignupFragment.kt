package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
	private lateinit var binding: FragmentSignupBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentSignupBinding.inflate(inflater, container, false)
		return binding.root
	}
}