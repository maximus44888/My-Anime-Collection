package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
	private lateinit var binding: FragmentContactBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentContactBinding.inflate(inflater, container, false)
		return binding.root
	}
}