package pujalte.martinez.juan.projectosegundaevaluacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pujalte.martinez.juan.projectosegundaevaluacion.data.Item

class ScaffoldViewModel : ViewModel() {
	
	private val _data = MutableLiveData<List<Item>>(listOf())
	val data: LiveData<List<Item>> get() = _data
	
	fun setData(items: List<Item>) {
		_data.value = items
	}
}