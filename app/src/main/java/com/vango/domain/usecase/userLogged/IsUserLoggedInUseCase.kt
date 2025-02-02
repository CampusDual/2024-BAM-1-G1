package com.vango.domain.usecase

import com.vango.domain.repository.UserRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.getUser()?.token?.isNotEmpty() == true
    }
}
