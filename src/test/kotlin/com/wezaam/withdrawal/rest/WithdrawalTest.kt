package com.wezaam.withdrawal.rest

import com.ninjasquad.springmockk.MockkBean
import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.service.WithdrawalService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant

@WebMvcTest
class WithdrawalTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var withdrawalService: WithdrawalService

    var withdrawal: Withdrawal = initWithdrawal()

    fun initWithdrawal(): Withdrawal {
        withdrawal = Withdrawal()
        withdrawal.id = 1001
        withdrawal.transactionId = 21525
        withdrawal.amount = 24.0
        withdrawal.createdAt = Instant.now()
        withdrawal.executeAt = Instant.now()
        withdrawal.userId = 1
        withdrawal.paymentMethodId = 1
        return withdrawal
    }

    @Test
    fun findAll_whenGetRequest_thenReturnsListOfWithdrawalWithStatus200() {
        every { withdrawalService.findAll() } returns listOf(withdrawal)

        mockMvc.perform(get("/withdrawals"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.transactionId").value(21525))
    }
}