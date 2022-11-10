package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.PaymentMethodNotFoundException
import com.wezaam.withdrawal.exception.UserNotFoundException
import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.model.WithdrawalStatus
import com.wezaam.withdrawal.repository.PaymentMethodRepository
import com.wezaam.withdrawal.repository.UserRepository
import com.wezaam.withdrawal.repository.WithdrawalRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class WithdrawalService(
        private val withdrawalRepository: WithdrawalRepository,
        private val userRepository: UserRepository,
        private val paymentMethodRepository: PaymentMethodRepository
) {

    fun findAll(): List<Withdrawal> {
        return withdrawalRepository.findAll()
    }

    fun create(withdrawal: Withdrawal): Withdrawal {
        userRepository.findById(withdrawal.userId).orElseThrow { throw UserNotFoundException(withdrawal.userId.toString()) }
        paymentMethodRepository.findById(withdrawal.paymentMethodId).orElseThrow { throw PaymentMethodNotFoundException(withdrawal.paymentMethodId.toString()) }
        withdrawal.status = WithdrawalStatus.PENDING
        return save(withdrawal)
    }

    fun save(withdrawal: Withdrawal): Withdrawal {
        return withdrawalRepository.save(withdrawal)
    }

    fun findAllByExecuteAtBeforeNow(): List<Withdrawal> {
        return withdrawalRepository.findAllByExecuteAtBefore(Instant.now());
    }
}