package com.wezaam.withdrawal.rest

import com.wezaam.withdrawal.repository.WithdrawalRepository
import com.wezaam.withdrawal.rest.request.WithdrawalRequest
import com.wezaam.withdrawal.rest.response.ApiResponseConverter
import com.wezaam.withdrawal.rest.response.WithdrawalResponse
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api
@RestController
class WithdrawalController(
        private val withdrawalScheduledRepository: WithdrawalRepository,
        private val apiResponsesConverter: ApiResponseConverter
) {

    @GetMapping("/withdrawals")
    fun findAll(): ResponseEntity<List<WithdrawalResponse>> {
        return ResponseEntity.ok(apiResponsesConverter.convertFromWithdrawals(withdrawalScheduledRepository.findAll()))
    }

    @PostMapping("/withdrawals")
    fun create(@Valid @RequestBody withdrawalRequest: WithdrawalRequest): ResponseEntity<WithdrawalResponse> {



        return ResponseEntity.badRequest().build()
    }
}