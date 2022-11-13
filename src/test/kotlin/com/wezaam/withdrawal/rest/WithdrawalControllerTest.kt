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
    lateinit var withdrawalServiceMock: WithdrawalService

    @MockkBean
    lateinit var userServiceMock: UserService

    @Autowired
    lateinit var responseConverterStub: ResponseConverter

    @Autowired
    lateinit var requestConverterStub: RequestConverter

    @MockkBean
    lateinit var withdrawalRequestMock: WithdrawalRequest

    private val mapperStub = jacksonObjectMapper()

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

        every { withdrawalServiceMock.findAll() } returns listOf(withdrawalStub)

        mockMvc.perform(get("/withdrawals"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("[0].transactionId").value(21525))
    }

    @Test
    fun create_whenValidPostRequest_thenReturnedSavedInstanceWithStatus200() {
        val withdrawalRequestStub = WithdrawalRequest("1", "1", "24.0", null)
        val createdWithdrawalStub = initWithdrawal(1001, 21525, 24.0, 1, 1, WithdrawalStatus.PENDING)

        every { withdrawalServiceMock.create(any()) } returns createdWithdrawalStub

        mockMvc.perform(
            post("/withdrawals").content(mapperStub.writeValueAsString(withdrawalRequestStub)).contentType(
                MediaType.APPLICATION_JSON
            )
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1001))
    }
}