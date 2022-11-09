package com.wezaam.withdrawal.rest.response

class UserResponse(
        var id: Long,
        var firstName: String,
        var paymentMethods: List<PaymentMethodResponse>,
        var maxWithdrawalAmount: Double
)