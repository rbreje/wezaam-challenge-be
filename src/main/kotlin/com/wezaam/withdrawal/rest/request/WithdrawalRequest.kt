package com.wezaam.withdrawal.rest.request

import java.time.Instant
import javax.validation.constraints.NotNull

data class WithdrawalRequest(
        @field:NotNull(message = "The user ID is missing.") val userId: Long,
        @field:NotNull(message = "The payment method ID is missing.") val paymentMethodId: Long,
        @field:NotNull(message = "The amount is missing.") val amount: Double,
        val executeAt: Instant
)