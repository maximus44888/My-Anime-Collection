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
	private val _data = MutableLiveData<List<Item>>(listOf())
	val data: LiveData<List<Item>> get() = _data
	
	init {
		viewModelScope.launch {
			val favorites = Firebase.firestore.collection("usuarios")
				.whereEqualTo("email", FirebaseAuth.getInstance().currentUser?.email).get().await()
				.run {
					if (isEmpty) {
						documents.first().get("favorites") as? List<String> ?: listOf()
					} else {
						listOf()
					}
				}
			
			val items = Firebase.firestore.collection("items").get().await().documents.map {
				Item(
					it.getString("title") ?: "",
					it.getString("description") ?: "",
					it.getString("image") ?: "",
					favorites.contains(it.getString("title"))
				)
			}
			
			_data.value = items
		}
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
}