package com.wezaam.withdrawal.rest.response

class UserResponse(
        val id: Long,
        val firstName: String,
        val paymentMethods: List<PaymentMethodResponse>,
        val maxWithdrawalAmount: Double
)