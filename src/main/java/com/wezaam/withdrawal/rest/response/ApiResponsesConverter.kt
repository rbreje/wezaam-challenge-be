package com.wezaam.withdrawal.rest.response

import com.wezaam.withdrawal.model.PaymentMethod
import com.wezaam.withdrawal.model.User

class ApiResponsesConverter {

    fun convertFromPaymentMethod(paymentMethod: PaymentMethod): PaymentMethodResponse {
        return PaymentMethodResponse(
                paymentMethod.id,
                paymentMethod.name
        )
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