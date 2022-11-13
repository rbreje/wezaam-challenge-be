package com.wezaam.withdrawal.rest.response

data class UserResponse(
    val id: Long,
    val firstName: String,
    val paymentMethods: List<PaymentMethodResponse>,
    val maxWithdrawalAmount: Double
)