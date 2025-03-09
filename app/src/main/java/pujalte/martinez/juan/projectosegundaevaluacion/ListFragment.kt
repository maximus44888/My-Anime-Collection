package pujalte.martinez.juan.projectosegundaevaluacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pujalte.martinez.juan.projectosegundaevaluacion.adapters.ItemAdapter
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item
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
		
		initialize(savedInstanceState)
	}
	
	private fun initialize(saveInstanceState: Bundle?) {
		val items = listOf(
			Item(
				"Naruto", getString(R.string.naruto_description), R.drawable.naruto
			),
			Item(
				"One Piece", getString(R.string.one_piece_description), R.drawable.one_piece
			),
			Item(
				"Bleach", getString(R.string.bleach_description), R.drawable.bleach
			),
			Item(
				"Dragon Ball", getString(R.string.dragon_ball_description), R.drawable.dragon_ball
			),
			Item(
				"Fairy Tail", getString(R.string.fairy_tail_description), R.drawable.fairy_tail
			),
			Item(
				"One Punch Man",
				getString(R.string.one_punch_man_description),
				R.drawable.one_punch_man
			),
			Item(
				"Hunter X Hunter",
				getString(R.string.hunter_x_hunter_description),
				R.drawable.hunter_x_hunter
			),
			Item(
				"Tokyo Ghoul", getString(R.string.tokyo_ghoul_description), R.drawable.tokyo_ghoul
			),
			Item(
				"Death Note", getString(R.string.death_note_description), R.drawable.death_note
			),
			Item(
				"Attack On Titan",
				getString(R.string.attack_on_titan_description),
				R.drawable.attack_on_titan
			),
		)
		
		binding.rv.layoutManager = LinearLayoutManager(requireContext())
		binding.rv.adapter = ItemAdapter(items)
	}
	
}