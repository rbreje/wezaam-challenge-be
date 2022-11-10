package com.wezaam.withdrawal.rest.request

import com.wezaam.withdrawal.model.Withdrawal
import java.time.Instant

class RequestConverter {

    fun convertFromWithdrawalRequest(withdrawalRequest: WithdrawalRequest): Withdrawal {
        val withdrawal = Withdrawal()
        withdrawal.userId = withdrawalRequest.userId
        withdrawal.paymentMethodId = withdrawalRequest.paymentMethodId
        withdrawal.amount = withdrawalRequest.amount
        withdrawal.createdAt = Instant.now()
        withdrawal.executeAt = if ("ASAP".equals(withdrawalRequest.executeAt)) Instant.now() else withdrawalRequest.executeAt
        return withdrawal
    }

}