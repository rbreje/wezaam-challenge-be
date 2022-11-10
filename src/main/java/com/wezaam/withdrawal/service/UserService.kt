package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.UserNotFoundException
import com.wezaam.withdrawal.model.User
import com.wezaam.withdrawal.repository.UserRepository

class UserService(
        private val userRepository: UserRepository
) {

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw UserNotFoundException(id.toString()) }
    }

}