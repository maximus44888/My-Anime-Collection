package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
	}
	
	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.main)
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}