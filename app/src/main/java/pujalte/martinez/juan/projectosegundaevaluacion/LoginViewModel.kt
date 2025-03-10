package pujalte.martinez.juan.projectosegundaevaluacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
	private val _user = MutableLiveData<String>()
	private val _password = MutableLiveData<String>()
	
	private val _isValidUser = MutableLiveData<Boolean>()
	val isValidUser: LiveData<Boolean> get() = _isValidUser
	
	private val _isValidPassword = MutableLiveData<Boolean>()
	val isValidPassword: LiveData<Boolean> get() = _isValidPassword
	
	private val _isValid = MutableLiveData<Boolean>()
	val isValid: LiveData<Boolean> get() = _isValid
	
	init {
		_user.value = ""
		_password.value = ""
		_isValid.value = false
		_isValidUser.value = true
		_isValidPassword.value = true
	}
	
	fun setUser(user: String) {
		_user.value = user
		validateUser()
	}
	
	fun setPassword(password: String) {
		_password.value = password
		validatePassword()
	}
	
	private fun validate() {
		_isValid.value = _isValidUser.value == true && _isValidPassword.value == true
	}
	
	private fun validateUser() {
		_isValidUser.value = !_user.value.isNullOrBlank()
		validate()
	}
	
	private fun validatePassword() {
		_isValidPassword.value = !_password.value.isNullOrBlank()
		validate()
	}
}