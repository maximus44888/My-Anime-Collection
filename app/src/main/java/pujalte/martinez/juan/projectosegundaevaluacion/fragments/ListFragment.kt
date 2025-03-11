package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.ScaffoldViewModel
import pujalte.martinez.juan.projectosegundaevaluacion.adapters.ItemAdapter
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
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		val viewModel = ViewModelProvider(
			requireParentFragment().requireParentFragment()
		)[ScaffoldViewModel::class.java]
		val adapter = ItemAdapter(viewModel)
		
		binding.rv.layoutManager = LinearLayoutManager(requireContext())
		binding.rv.adapter = adapter
		
		viewModel.data.observe(viewLifecycleOwner) {
			adapter.updateData(it)
		}
	}
	
}