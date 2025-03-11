package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.activity_main_root) as NavHostFragment).navController }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		val splashScreen = installSplashScreen()
		var keepOnScreenCondition = true
		splashScreen.setKeepOnScreenCondition { keepOnScreenCondition }
		Handler(Looper.getMainLooper()).postDelayed({
			keepOnScreenCondition = false
		}, 3000)
		
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.activityMainRoot) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		
		if (FirebaseAuth.getInstance().currentUser != null) {
			navController.navigate(R.id.action_loginFragment_to_scaffoldFragment)
		}
	}
	
	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}