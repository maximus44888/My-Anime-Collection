package pujalte.martinez.juan.projectosegundaevaluacion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.LayoutItemBinding
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.ScaffoldViewModel

class ItemAdapter(
	private val scaffoldViewModel: ScaffoldViewModel,
	private val predicate: (Item) -> Boolean = { true },
) :
	RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
	
	private var filteredItems: List<Item> = emptyList()
	
	inner class ItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
		val image = binding.itemImage
		val title = binding.itemTitle
		val description = binding.itemDescription
		val favButton = binding.itemFavButton
		
		fun bind(item: Item) {
			title.text = item.title
			description.text = item.description
			favButton.setImageResource(if (item.isFavorite) R.drawable.fav_selected else R.drawable.fav_unselected)
			favButton.setOnClickListener {
				scaffoldViewModel.toggleFavorite(item)
				favButton.setImageResource(if (item.isFavorite) R.drawable.fav_selected else R.drawable.fav_unselected)
			}
		}
	}
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int,
	): ItemViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		
		return ItemViewHolder(LayoutItemBinding.inflate(layoutInflater, parent, false))
	}
	
	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		holder.bind(filteredItems[position])
	}
	
	override fun getItemCount() = filteredItems.size
	
	fun updateData(newData: List<Item>) {
		filteredItems = newData.filter(predicate)
		notifyDataSetChanged()
	}
}