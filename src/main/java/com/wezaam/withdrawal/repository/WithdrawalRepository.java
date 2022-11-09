package com.wezaam.withdrawal.repository;

import com.wezaam.withdrawal.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
