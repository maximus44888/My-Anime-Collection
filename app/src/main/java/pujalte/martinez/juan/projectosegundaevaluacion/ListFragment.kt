package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentListBinding

class ListFragment : Fragment() {
	private lateinit var binding: FragmentListBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentListBinding.inflate(inflater, container, false)
		return binding.root
	}
}