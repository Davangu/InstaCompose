package com.pdm.instacompose.login.domain

import com.pdm.instacompose.login.data.LoginRepository

class LoginUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(user:String, password:String):Boolean {
        return repository.doLogin(user, password)
    }
}