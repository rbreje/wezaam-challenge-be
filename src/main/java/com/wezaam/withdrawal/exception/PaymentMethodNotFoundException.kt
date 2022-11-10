package com.wezaam.withdrawal.exception

class PaymentMethodNotFoundException(id: String) : RuntimeException("Payment Method $id does not exist.")