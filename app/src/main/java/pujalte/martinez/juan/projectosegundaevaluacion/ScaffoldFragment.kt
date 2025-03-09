package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentScaffoldBinding

class ScaffoldFragment : Fragment() {
	private lateinit var binding: FragmentScaffoldBinding
	private val navController by lazy { (childFragmentManager.findFragmentById(R.id.fragment_scaffold_fragment_container_view) as NavHostFragment).navController }
	
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
		
		setupMaterialToolbar()
		
		setupNavigationView()
		
		setupBottomNavigationView()
	}
	
	private fun setupMaterialToolbar() {
		(activity as AppCompatActivity).setSupportActionBar(binding.fragmentScaffoldMaterialToolbar)
		
		binding.fragmentScaffoldMaterialToolbar.setNavigationOnClickListener {
			findNavController().navigateUp()
		}
		
		activity?.addMenuProvider(object : MenuProvider {
			override fun onCreateMenu(
				menu: Menu,
				menuInflater: MenuInflater,
			) {
				menuInflater.inflate(R.menu.material_toolbar_menu, menu)
				
				val searchView =
					menu.findItem(R.id.material_toolbar_menu_item_search).actionView as SearchView
				searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
					override fun onQueryTextSubmit(query: String?): Boolean {
						Log.d("ScaffoldFragment", "Search query: $query")
						return true
					}
					
					override fun onQueryTextChange(newText: String?): Boolean {
						Log.d("ScaffoldFragment", "Search query changed: $newText")
						return true
					}
				})
			}
			
			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return when (menuItem.itemId) {
					R.id.material_toolbar_menu_item_search -> {
						Log.d("ScaffoldFragment", "Search button clicked")
						true
					}
					
					R.id.material_toolbar_menu_item_sort -> {
						Log.d("ScaffoldFragment", "Sort button clicked")
						true
					}
					
					R.id.material_toolbar_menu_item_logout -> {
						findNavController().navigate(R.id.action_scaffoldFragment_to_loginFragment)
						true
					}
					
					else -> false
				}
			}
			
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
	}
	
	private fun setupNavigationView() {
		setupActionBarWithNavController(
			activity as AppCompatActivity,
			navController,
			AppBarConfiguration(
				setOf(R.id.navigation_list, R.id.navigation_favorites, R.id.navigation_contact),
				binding.fragmentScaffoldDrawerLayout
			)
		)
		binding.fragmentScaffoldNavigationView.setupWithNavController(navController)
		/*binding.fragmentScaffoldNavigationView.setNavigationItemSelectedListener {
			when (it.itemId) {
				R.id.navigation_logout -> {
					binding.fragmentScaffoldDrawerLayout.closeDrawers()
					findNavController().navigate(R.id.action_scaffoldFragment_to_loginFragment)
					true
				}
				else -> false
			}
		}*/
		
		val toggle = ActionBarDrawerToggle(
			activity,
			binding.fragmentScaffoldDrawerLayout,
			binding.fragmentScaffoldMaterialToolbar,
			R.string.navigation_drawer_open,
			R.string.navigation_drawer_close
		)
		binding.fragmentScaffoldDrawerLayout.addDrawerListener(toggle)
		toggle.syncState()
	}
	
	private fun setupBottomNavigationView() {
		binding.fragmentScaffoldBottomNavigationView.setupWithNavController(navController)
	}
}