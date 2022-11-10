package com.wezaam.withdrawal.rest.response

import com.wezaam.withdrawal.model.WithdrawalStatus
import java.time.Instant

data class WithdrawalResponse(
        val id: Long,
        val transactionId: Long?,
        val amount: Double,
        val createAt: Instant,
        val executeAt: Instant,
        val userId: Long,
        val paymentMethodId: Long,
        val status: WithdrawalStatus
)