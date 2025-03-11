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
	
	init {
		viewModelScope.launch {
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
		}
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