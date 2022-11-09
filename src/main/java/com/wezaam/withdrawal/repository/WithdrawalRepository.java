package com.wezaam.withdrawal.repository;

import com.wezaam.withdrawal.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;


public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findAllByExecuteAtBefore(Instant date);
}
