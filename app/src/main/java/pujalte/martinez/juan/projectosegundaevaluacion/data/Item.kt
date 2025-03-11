package pujalte.martinez.juan.projectosegundaevaluacion.data

data class Item(
	val title: String,
	val description: String,
	val image: String,
	var isFavorite: Boolean = false,
)
