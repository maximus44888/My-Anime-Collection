package pujalte.martinez.juan.projectosegundaevaluacion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item

class ScaffoldViewModel(
	initialData: List<Item> = listOf()
) : ViewModel() {
	
	private val _data = MutableLiveData<List<Item>>(listOf())
	val data: LiveData<List<Item>> get() = _data
	
	init {
		setData(initialData)
	}
	
	fun setData(items: List<Item>) {
		_data.value = items
	}
	
	fun toggleFavorite(item: Item) {
		val currentList = _data.value?.toMutableList() ?: mutableListOf()
		val index = currentList.indexOfFirst { it == item }
		
		if (index != -1) {
			val currentItem = currentList[index]
			currentList[index] = currentItem.copy(
				isFavorite = !currentItem.isFavorite
			)
			_data.value = currentList
		}
	}
	
	class Factory(
		private val initialData: List<Item> = listOf()
	) : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			if (modelClass.isAssignableFrom(ScaffoldViewModel::class.java)) {
				return ScaffoldViewModel(initialData) as T
			}
			throw IllegalArgumentException("Unknown ViewModel class")
		}
	}
}