package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.model.WithdrawalStatus
import com.wezaam.withdrawal.repository.WithdrawalRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class WithdrawalService(
        private val withdrawalRepository: WithdrawalRepository,
        private val userService: UserService,
        private val paymentMethodService: PaymentMethodService
) {

    fun findAll(): List<Withdrawal> {
        return withdrawalRepository.findAll()
    }

    fun create(withdrawal: Withdrawal): Withdrawal {
        userService.findById(withdrawal.userId)
        paymentMethodService.findById(withdrawal.paymentMethodId)
        withdrawal.status = WithdrawalStatus.PENDING
        return save(withdrawal)
    }

    fun save(withdrawal: Withdrawal): Withdrawal {
        return withdrawalRepository.save(withdrawal)
    }

    fun findAllByExecuteAtBeforeNow(): List<Withdrawal> {
        return withdrawalRepository.findAllByExecuteAtBefore(Instant.now())
    }
}