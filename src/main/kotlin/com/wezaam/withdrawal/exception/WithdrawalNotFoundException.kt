package com.wezaam.withdrawal.exception

class WithdrawalNotFoundException(id: String) : RuntimeException("Withdrawals $id does not exist.")