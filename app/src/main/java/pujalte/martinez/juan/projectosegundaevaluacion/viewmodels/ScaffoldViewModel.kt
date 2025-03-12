package pujalte.martinez.juan.projectosegundaevaluacion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item

class ScaffoldViewModel : ViewModel() {
	private val _items = MutableLiveData<List<Item>>(listOf())
	val items: LiveData<List<Item>> get() = _items
	
	private var _filter : MutableLiveData<(Item) -> Boolean> = MutableLiveData { true }
	val filter : LiveData<(Item) -> Boolean> get() = _filter
	
	private var _sort : MutableLiveData<Comparator<Item>> = MutableLiveData(compareBy { it.title })
	val sort : LiveData<Comparator<Item>> get() = _sort
	
	private var isSortAscending = true
	
	private var _isLoading = MutableLiveData(true)
	val isLoading: LiveData<Boolean> get() = _isLoading
	
	init {
		loadData()
	}
	
	fun loadData() {
		viewModelScope.launch {
			_isLoading.value = true
			
			val favorites = Firebase.firestore.collection("usuarios")
				.whereEqualTo("email", FirebaseAuth.getInstance().currentUser?.email).get().await()
				.run {
					if (!isEmpty) {
						documents.first().get("favorites") as? List<*>
							?: listOf<Any>()
					} else {
						listOf<Any>()
					}
				}
			
			_items.value = Firebase.firestore.collection("items").get().await().documents.map {
				Item(
					it.getString("title") ?: "",
					it.getString("description") ?: "",
					it.getString("image") ?: "",
					favorites.contains(it.reference)
				)
			}
			
			_isLoading.value = false
		}
	}
	
	fun setFilter(newFilter: (Item) -> Boolean) {
		_filter.value = newFilter
	}
	
	fun setSort(newSort: Comparator<Item>) {
		_sort.value = newSort
	}
	
	fun toggleSort() {
		isSortAscending = !isSortAscending
		_sort.value = if (isSortAscending) compareBy { it.title } else compareByDescending { it.title }
	}
	
	fun toggleFavorite(item: Item) {
		val currentList = _items.value?.toMutableList() ?: mutableListOf()
		val index = currentList.indexOfFirst { it == item }
		
		if (index != -1) {
			val currentItem = currentList[index]
			val newFavoriteStatus = !currentItem.isFavorite
			
			currentList[index] = currentItem.copy(
				isFavorite = newFavoriteStatus
			)
			
			viewModelScope.launch {
				val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: return@launch
				val userDoc = Firebase.firestore.collection("usuarios")
					.whereEqualTo("email", userEmail).get().await().documents.firstOrNull()
					?: return@launch
				val itemRef = Firebase.firestore.collection("items")
					.whereEqualTo("title", item.title).get()
					.await().documents.firstOrNull()?.reference ?: return@launch
				
				val newFavorites =
					(userDoc.get("favorites") as? List<*> ?: listOf<Any>()).let { prevFavorites ->
						if (newFavoriteStatus) {
							if (!prevFavorites.contains(itemRef)) prevFavorites + itemRef
							else prevFavorites
						} else {
							prevFavorites.filter { it != itemRef }
						}
					}
				
				Firebase.firestore.collection("usuarios").document(userDoc.id)
					.update("favorites", newFavorites)
			}
		}
		
		_items.value = currentList
	}
}