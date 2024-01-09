package com.pdm.instacompose.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.instacompose.login.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    // Este se crea para acceder solo desde dentro del viewmodel
    private val _email = MutableLiveData<String>()
    // Y ahora creamos el público, que será accesible desde otras partes de la APP
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val loginUseCase = LoginUseCase()

    fun onLoginChange(email:String, password:String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)
    }

    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

    fun onLoginSelected() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase(email.value.orEmpty(),password.value.orEmpty())
            if(result) Log.i("Pruebalogin","result ok")

            _isLoading.value = false
        }
    }
}