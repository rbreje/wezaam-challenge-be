package com.wezaam.withdrawal.exception

class WithdrawalNotFoundException(id: String) : Exception("Withdrawals $id does not exist.")