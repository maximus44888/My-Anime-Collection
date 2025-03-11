package pujalte.martinez.juan.projectosegundaevaluacion.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
	private lateinit var binding: FragmentContactBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentContactBinding.inflate(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		initialize(savedInstanceState)
	}
	
	private fun initialize(savedInstanceState: Bundle?) {
		fun setupClickableToIntentWithPermissions(
			clickable: View,
			editText: EditText,
			permission: String?,
			intentSupplier: (clickable: EditText) -> Intent,
			rationalePermission: String = getString(R.string.default_rationale_permission),
			rationaleUnavailability: String = getString(R.string.default_rationale_unavailability),
			unableToResolveActivityMessage: String = getString(R.string.default_unable_to_resolve_activity_message),
		) {
			fun runIntent() {
				val intent = intentSupplier(editText)
				if (intent.resolveActivity(requireContext().packageManager) != null) {
					startActivity(intent)
				} else {
					Snackbar.make(
						binding.root, unableToResolveActivityMessage, Snackbar.LENGTH_LONG
					).show()
				}
			}
			
			val onClickListener = if (permission == null) {
				View.OnClickListener {
					runIntent()
				}
			} else {
				val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
					if (it) {
						runIntent()
					} else {
						Snackbar.make(
							binding.root, rationaleUnavailability, Snackbar.LENGTH_LONG
						).show()
					}
				}
				View.OnClickListener {
					when {
						ContextCompat.checkSelfPermission(
							requireContext(), permission
						) == PERMISSION_GRANTED -> {
							runIntent()
						}
						
						shouldShowRequestPermissionRationale(permission) -> {
							Snackbar.make(
								binding.root, rationalePermission, Snackbar.LENGTH_LONG
							).show()
						}
						
						else -> {
							requestPermissionLauncher.launch(permission)
						}
					}
					
				}
			}
			
			clickable.setOnClickListener(onClickListener)
		}
		
		setupClickableToIntentWithPermissions(
			binding.whatsappButton,
			binding.phone,
			null,
			{ editText ->
				Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${editText.text}")).apply {
					setPackage("com.whatsapp")
				}
			},
			unableToResolveActivityMessage = getString(R.string.whatsapp_unable_to_resolve_activity_message)
		)
		setupClickableToIntentWithPermissions(
			binding.phoneButton,
			binding.phone,
			Manifest.permission.CALL_PHONE,
			{ editText ->
				Intent(Intent.ACTION_CALL, Uri.parse("tel:${editText.text}"))
			},
			getString(R.string.phone_rationale_permission),
			getString(R.string.phone_rationale_unavailability),
			getString(R.string.phone_unable_to_resolve_activity_message)
		)
		setupClickableToIntentWithPermissions(
			binding.emailButton,
			binding.email,
			null,
			{ editText ->
				Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${editText.text}"))
			},
			unableToResolveActivityMessage = getString(R.string.email_unable_to_resolve_activity_message)
		)
		setupClickableToIntentWithPermissions(
			binding.locationButton,
			binding.location,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			{ editText ->
				Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${editText.text}"))
			},
			getString(R.string.location_rationale_permission),
			getString(R.string.location_rationale_unavailability),
			getString(R.string.location_unable_to_resolve_activity_message)
		)
		setupClickableToIntentWithPermissions(
			binding.map,
			binding.location,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			{ editText ->
				Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${editText.text}"))
			},
			getString(R.string.location_rationale_permission),
			getString(R.string.location_rationale_unavailability),
			getString(R.string.location_unable_to_resolve_activity_message)
		)
	}
	
}