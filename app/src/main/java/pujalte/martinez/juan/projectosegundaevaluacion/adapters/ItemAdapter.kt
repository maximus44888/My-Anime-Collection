package pujalte.martinez.juan.projectosegundaevaluacion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.LayoutItemBinding

class ItemAdapter(private var items: List<Item>) :
	RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
	
	class ItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
		private val image = binding.itemImage
		private val title = binding.itemTitle
		private val description = binding.itemDescription
		private val favButton = binding.itemFavButton
		
		fun bind(item: Item) {
			image.setImageResource(item.image)
			title.text = item.title
			description.text = item.description
			if (item.isFavorite) {
				favButton.setImageResource(R.drawable.fav_selected)
			} else {
				favButton.setImageResource(R.drawable.fav_unselected)
			}
			favButton.setOnClickListener {
				item.isFavorite = !item.isFavorite
				if (item.isFavorite) {
					favButton.setImageResource(R.drawable.fav_selected)
				} else {
					favButton.setImageResource(R.drawable.fav_unselected)
				}
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
		holder.bind(items[position])
	}
	
	override fun getItemCount(): Int {
		return items.size
	}
	
	fun updateItems(newItems: List<Item>) {
		items = newItems
		notifyDataSetChanged()
	}
}