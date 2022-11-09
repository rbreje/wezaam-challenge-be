package com.wezaam.withdrawal.rest

import com.wezaam.withdrawal.repository.WithdrawalRepository
import com.wezaam.withdrawal.rest.response.ApiResponsesConverter
import com.wezaam.withdrawal.rest.response.WithdrawalResponse
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Api
@RestController(value = "/api")
class WithdrawalController(
        private val withdrawalScheduledRepository: WithdrawalRepository,
        private val apiResponsesConverter: ApiResponsesConverter
) {

    @GetMapping("/withdrawals")
    fun findAll(): ResponseEntity<List<WithdrawalResponse>> {
        return ResponseEntity.ok(apiResponsesConverter.convertFromWithdrawals(withdrawalScheduledRepository.findAll()))
    }

}