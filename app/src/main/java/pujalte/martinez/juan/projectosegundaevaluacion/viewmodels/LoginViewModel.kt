package pujalte.martinez.juan.projectosegundaevaluacion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class LoginViewModel(
	initialUser: String = "",
	initialPassword: String = "",
) : ViewModel() {
	private val _user = MutableLiveData<String>()
	val user: LiveData<String> get() = _user
	
	private val _password = MutableLiveData<String>()
	val password: LiveData<String> get() = _password
	
	private val _birthday = MutableLiveData<Calendar>(Calendar.getInstance())
	val birthday: LiveData<Calendar> get() = _birthday
	
	private val _isValidUser = MutableLiveData<Boolean>()
	val isValidUser: LiveData<Boolean> get() = _isValidUser
	
	private val _isValidPassword = MutableLiveData<Boolean>()
	val isValidPassword: LiveData<Boolean> get() = _isValidPassword
	
	private val _isValid = MutableLiveData<Boolean>()
	val isValid: LiveData<Boolean> get() = _isValid
	
	init {
		setUser(initialUser)
		setPassword(initialPassword)
	}
	
	fun setUser(user: String) {
		_user.value = user
		validateUser()
	}
	
	fun setPassword(password: String) {
		_password.value = password
		validatePassword()
	}
	
	fun setBirthday(birthday: Calendar) {
		_birthday.value = birthday
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
	
	class Factory(
		private val initialUser: String = "",
		private val initialPassword: String = "",
	) : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
				return LoginViewModel(initialUser, initialPassword) as T
			}
			throw IllegalArgumentException("Unknown ViewModel class")
		}
	}
}