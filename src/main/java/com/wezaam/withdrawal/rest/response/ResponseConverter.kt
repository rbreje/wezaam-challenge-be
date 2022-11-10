package com.wezaam.withdrawal.rest.response

import com.wezaam.withdrawal.model.PaymentMethod
import com.wezaam.withdrawal.model.User
import com.wezaam.withdrawal.model.Withdrawal

class ResponseConverter {

    fun convertFromWithdrawals(withdrawals: List<Withdrawal>): List<WithdrawalResponse> {
        return withdrawals.map {
            WithdrawalResponse(
                    it.id,
                    it.transactionId,
                    it.amount,
                    it.createdAt,
                    it.executeAt,
                    it.userId,
                    it.paymentMethodId,
                    it.status
            )
        }
    }

    fun convertFromUser(user: User): UserResponse {
        return UserResponse(
                user.id,
                user.firstName,
                convertFromPaymentMethods(user.paymentMethods),
                user.maxWithdrawalAmount
        )
    }

    private fun convertFromPaymentMethods(paymentMethods: List<PaymentMethod>): List<PaymentMethodResponse> {
        return paymentMethods.map { PaymentMethodResponse(it.id, it.name) };
    }
}