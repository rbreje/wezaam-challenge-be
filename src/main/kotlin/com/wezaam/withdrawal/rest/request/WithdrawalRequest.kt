package com.wezaam.withdrawal.rest.request

import javax.validation.constraints.NotNull

class WithdrawalRequest(
    @field:NotNull(message = "The user ID is missing.") val userId: String,
    @field:NotNull(message = "The payment method ID is missing.") val paymentMethodId: String,
    @field:NotNull(message = "The amount is missing.") val amount: String,
    val executeAt: String?
)