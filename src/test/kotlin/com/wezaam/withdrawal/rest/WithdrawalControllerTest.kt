package com.wezaam.withdrawal.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.wezaam.withdrawal.config.SwaggerConfig
import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.model.WithdrawalStatus
import com.wezaam.withdrawal.rest.request.RequestConverter
import com.wezaam.withdrawal.rest.request.WithdrawalRequest
import com.wezaam.withdrawal.rest.response.ResponseConverter
import com.wezaam.withdrawal.service.UserService
import com.wezaam.withdrawal.service.WithdrawalService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant

@Import(SwaggerConfig::class)
@WebMvcTest(WithdrawalController::class)
class WithdrawalControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var withdrawalService: WithdrawalService

    @MockkBean
    lateinit var userService: UserService

    @Autowired
    lateinit var responseConverter: ResponseConverter

    @Autowired
    lateinit var requestConverter: RequestConverter

    @MockkBean
    lateinit var withdrawalRequest: WithdrawalRequest

    private val mapper = jacksonObjectMapper()

    fun initWithdrawal(
        id: Long,
        transactionId: Long,
        amount: Double,
        userId: Long,
        paymentMethodId: Long,
        status: WithdrawalStatus
    ): Withdrawal {
        val withdrawal = Withdrawal()
        withdrawal.id = id
        withdrawal.transactionId = transactionId
        withdrawal.amount = amount
        withdrawal.createdAt = Instant.now()
        withdrawal.executeAt = Instant.now()
        withdrawal.userId = userId
        withdrawal.paymentMethodId = paymentMethodId
        withdrawal.status = status
        return withdrawal
    }

    @Test
    fun findAll_whenGetRequest_thenReturnsListOfWithdrawalWithStatus200() {
        val withdrawalStub = initWithdrawal(
            1001,
            21525,
            24.0,
            1,
            1,
            WithdrawalStatus.PENDING
        )

        every { withdrawalService.findAll() } returns listOf(withdrawalStub)

        mockMvc.perform(get("/withdrawals"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("[0].transactionId").value(21525))
    }

    @Test
    fun create_whenValidPostRequest_thenReturnedSavedInstanceWithStatus200() {
        val withdrawalRequest = WithdrawalRequest(1, 1, 24.0, "ASAP")
        val createdWithdrawalStub = initWithdrawal(1001, 21525, 24.0, 1, 1, WithdrawalStatus.PENDING)

        every { withdrawalService.create(any()) } returns createdWithdrawalStub

        mockMvc.perform(
            post("/withdrawals").content(mapper.writeValueAsString(withdrawalRequest)).contentType(
                MediaType.APPLICATION_JSON
            )
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1001))
    }
}