package com.wezaam.withdrawal.rest

import com.wezaam.withdrawal.rest.request.RequestConverter
import com.wezaam.withdrawal.rest.request.WithdrawalRequest
import com.wezaam.withdrawal.rest.response.ResponseConverter
import com.wezaam.withdrawal.rest.response.WithdrawalResponse
import com.wezaam.withdrawal.service.WithdrawalService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api
@RestController
class WithdrawalController(
    private val responseConverter: ResponseConverter,
    private val requestConverter: RequestConverter,
    private val withdrawalService: WithdrawalService
) {

    @GetMapping("/withdrawals")
    fun findAll(): ResponseEntity<List<WithdrawalResponse>> {
        return ResponseEntity.ok(responseConverter.convertFromWithdrawals(withdrawalService.findAll()))
    }

    @GetMapping("/withdrawals/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<WithdrawalResponse> {
        val withdrawal = withdrawalService.findById(id)
        val withdrawalResponse = responseConverter.convertFromWithdrawal(withdrawal)
        return ResponseEntity(withdrawalResponse, HttpStatus.OK)
    }

    @PostMapping("/withdrawals")
    fun create(@Valid @RequestBody withdrawalRequest: WithdrawalRequest): ResponseEntity<WithdrawalResponse> {
        val withdrawal = requestConverter.convertFromWithdrawalRequest(withdrawalRequest)
        val savedWithdrawal = withdrawalService.create(withdrawal)
        val withdrawalResponse = responseConverter.convertFromWithdrawal(savedWithdrawal)
        return ResponseEntity(withdrawalResponse, HttpStatus.CREATED)
    }
}