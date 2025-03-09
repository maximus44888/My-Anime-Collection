package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentScaffoldBinding

class ScaffoldFragment : Fragment() {
	private lateinit var binding: FragmentScaffoldBinding
	private val navController by lazy { binding.fragmentScaffoldFragmentContainerView.findNavController() }
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentScaffoldBinding.inflate(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		setupBottomNavigationView()
	}
	
	private fun setupBottomNavigationView() {
		binding.fragmentScaffoldBottomNavigationView.setOnItemSelectedListener {
			when (it.itemId) {
				R.id.bottom_navigation_view_menu_item_list -> {
					navController.navigate(R.id.listFragment)
					true
				}
				
				R.id.bottom_navigation_view_menu_item_favorites -> {
					navController.navigate(R.id.favoritesFragment)
					true
				}
				
				R.id.bottom_navigation_view_menu_item_contact -> {
					navController.navigate(R.id.contactFragment)
					true
				}
				
				else -> false
			}
		}
	}
}