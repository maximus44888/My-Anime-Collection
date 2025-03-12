package pujalte.martinez.juan.projectosegundaevaluacion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.LayoutItemBinding
import pujalte.martinez.juan.projectosegundaevaluacion.viewmodels.ScaffoldViewModel

class ItemAdapter(
	private val requestManager: RequestManager,
	private val scaffoldViewModel: ScaffoldViewModel,
	private val permanentPredicate: (Item) -> Boolean = { true },
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
	var filter: (Item) -> Boolean = { true }
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	var sort: Comparator<Item> = compareBy { it.title }
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	var items: List<Item> =
		scaffoldViewModel.items.value ?: listOf()
		get() {
			return field.filter(permanentPredicate).filter(filter).sortedWith(sort)
		}
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	
	inner class ItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
		val image = binding.itemImage
		val title = binding.itemTitle
		val description = binding.itemDescription
		val favButton = binding.itemFavButton
		
		fun bind(item: Item) {
			requestManager.load(item.image).into(image)
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
		holder.bind(items[position])
	}
	
	override fun getItemCount() = items.size
}