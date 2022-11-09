package com.wezaam.withdrawal.rest.request

import java.time.Instant
import javax.validation.constraints.NotBlank

data class WithdrawalRequest(
        @NotBlank(message = "The user ID is missing.") val userId: Long,
        @NotBlank(message = "The payment method ID is missing.") val paymentMethodId: Long,
        @NotBlank(message = "The amount is missing.") val amount: Double,
        val executeAt: Instant
)