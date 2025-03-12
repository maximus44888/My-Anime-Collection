package pujalte.martinez.juan.projectosegundaevaluacion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pujalte.martinez.juan.projectosegundaevaluacion.R
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item
import pujalte.martinez.juan.projectosegundaevaluacion.databinding.LayoutItemBinding

class ItemAdapter(
	private val initialItems: List<Item> = listOf(),
	private val initialFilter: (Item) -> Boolean = { true },
	private val initialSort: Comparator<Item> = compareBy { it.title },
	private val permanentPredicate: (Item) -> Boolean = { true },
	private val binder: (ItemViewHolder, Item) -> Unit,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
	var items: List<Item> = initialItems
		get() {
			return field.filter(permanentPredicate).filter(filter).sortedWith(sort)
		}
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	var filter: (Item) -> Boolean = initialFilter
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	var sort: Comparator<Item> = initialSort
		set(value) {
			field = value
			notifyDataSetChanged()
		}
	
	inner class ItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
		val image = binding.itemImage
		val title = binding.itemTitle
		val description = binding.itemDescription
		val favButton = binding.itemFavButton
	}
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int,
	): ItemViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		
		return ItemViewHolder(LayoutItemBinding.inflate(layoutInflater, parent, false))
	}
	
	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		binder(holder, items[position])
	}
	
	override fun getItemCount() = items.size
}