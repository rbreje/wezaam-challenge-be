package com.wezaam.withdrawal.exception

class UserNotFoundException(id: String) : RuntimeException("User $id does not exist.")