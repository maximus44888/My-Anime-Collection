package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pujalte.martinez.juan.projectosegundaevaluacion.adapters.ItemAdapter
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
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		initialize(savedInstanceState)
	}
	
	private fun initialize(saveInstanceState: Bundle?) {
		val viewModel = ViewModelProvider(requireActivity())[ScaffoldViewModel::class.java]
		val adapter = ItemAdapter({ viewModel.toggleFavorite(it) }) { it.isFavorite }
		
		binding.rv.layoutManager = LinearLayoutManager(requireContext())
		binding.rv.adapter = adapter
		
		viewModel.data.observe(viewLifecycleOwner) {
			adapter.updateData(it)
		}
	}
}