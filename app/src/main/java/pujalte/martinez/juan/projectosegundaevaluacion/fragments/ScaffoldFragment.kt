package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentScaffoldBinding
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.ScaffoldViewModel

class ScaffoldFragment : Fragment() {
	private lateinit var binding: FragmentScaffoldBinding
	private val navController by lazy { (childFragmentManager.findFragmentById(R.id.fragment_scaffold_fragment_container_view) as NavHostFragment).navController }
	private val scaffoldViewModel by lazy { ViewModelProvider(this)[ScaffoldViewModel::class.java] }
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		scaffoldViewModel
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
						if (newText == null) {
							scaffoldViewModel.setFilter { true }
						} else {
							scaffoldViewModel.setFilter {
								it.title.contains(
									newText,
									ignoreCase = true
								)
							}
						}
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
						scaffoldViewModel.toggleSort()
						true
					}
					
					R.id.material_toolbar_menu_item_logout -> {
						FirebaseAuth.getInstance().signOut()
						activity?.viewModelStore?.clear()
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
					FirebaseAuth.getInstance().signOut()
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
		
		val userName = FirebaseAuth.getInstance().currentUser?.email
		
		if (userName != null) {
			binding.fragmentScaffoldNavigationView.getHeaderView(0)
				.findViewById<TextView>(R.id.textViewName).text = userName
		}
	}
	
	private fun setupBottomNavigationView() {
		binding.fragmentScaffoldBottomNavigationView.setupWithNavController(navController)
	}
	
	override fun onResume() {
		super.onResume()
		
		scaffoldViewModel.loadData()
	}
}