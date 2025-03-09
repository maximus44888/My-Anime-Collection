package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
	private lateinit var binding: FragmentFavoritesBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentFavoritesBinding.inflate(inflater, container, false)
		return binding.root
	}
}