package com.wezaam.withdrawal.rest.request

import com.wezaam.withdrawal.model.Withdrawal
import java.time.Instant

class RequestConverter {

    fun convertFromWithdrawalRequest(withdrawalRequest: WithdrawalRequest): Withdrawal {
        val withdrawal = Withdrawal()
        withdrawal.userId = withdrawalRequest.userId.toLong()
        withdrawal.paymentMethodId = withdrawalRequest.paymentMethodId.toLong()
        withdrawal.amount = withdrawalRequest.amount.toDouble()
        withdrawal.createdAt = Instant.now()
        withdrawal.executeAt =
            if (withdrawalRequest.executeAt.isNullOrBlank()) Instant.now() else Instant.parse(withdrawalRequest.executeAt)
        return withdrawal
    }

}