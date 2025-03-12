package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import pujalte.martinez.juan.projectosegundaevaluacion.adapters.ItemAdapter
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentFavoritesBinding
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.ScaffoldViewModel

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
		
		val viewModel = ViewModelProvider(
			requireParentFragment().requireParentFragment()
		)[ScaffoldViewModel::class.java]
		val adapter = ItemAdapter(Glide.with(this), viewModel) { it.isFavorite }
		
		binding.rv.layoutManager = LinearLayoutManager(requireContext())
		binding.rv.adapter = adapter
		
		viewModel.filter.observe(viewLifecycleOwner) {
			adapter.filter = it
		}
		viewModel.sort.observe(viewLifecycleOwner) {
			adapter.sort = it
		}
		viewModel.items.observe(viewLifecycleOwner) {
			adapter.items = it
		}
	}
}