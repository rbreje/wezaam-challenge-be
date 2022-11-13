package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.PaymentMethodNotFoundException
import com.wezaam.withdrawal.model.PaymentMethod
import com.wezaam.withdrawal.model.Withdrawal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class WithdrawalExecutorTest {

    val withdrawalServiceMock: WithdrawalService = mockk()
    val paymentMethodServiceMock: PaymentMethodService = mockk()
    val withdrawalProcessingServiceMock: WithdrawalProcessingService = mockk()
    val eventsServiceMock: EventsService = mockk()
    val paymentMethodMock: PaymentMethod = mockk()

    val withdrawalExecutor = WithdrawalExecutor(
        withdrawalServiceMock, paymentMethodServiceMock, withdrawalProcessingServiceMock, eventsServiceMock
    )

    @Test
    fun execute_whenValidWithdrawal_thenSaveNewStatus() {
        val withdrawalMock1: Withdrawal = mockk()
        val withdrawalMock2: Withdrawal = mockk()
        every { withdrawalMock1.paymentMethodId } returns 525
        every { withdrawalMock1.amount } returns 56.2
        every { withdrawalMock1.status }
        every { withdrawalMock1.transactionId }
        every { paymentMethodServiceMock.findById(525) } returns paymentMethodMock
        every { withdrawalProcessingServiceMock.sendToProcessing(56.2, paymentMethodMock) } returns System.nanoTime()
        every { withdrawalServiceMock.save(withdrawalMock1) } returns withdrawalMock2

        withdrawalExecutor.execute(withdrawalMock1)

        verify { paymentMethodServiceMock.findById(525) }
        verify { withdrawalProcessingServiceMock.sendToProcessing(56.2, paymentMethodMock) }
        verify { eventsServiceMock.send(withdrawalMock2) }
    }

    @Test
    fun execute_whenExceptionOccurs_thenSetStatus() {
        val withdrawalMock1: Withdrawal = mockk()
        val withdrawalMock2: Withdrawal = mockk()
        every { withdrawalMock1.status }
        every { withdrawalMock1.paymentMethodId } returns 524
        every { paymentMethodServiceMock.findById(524) } throws PaymentMethodNotFoundException("524")
        every { withdrawalServiceMock.save(withdrawalMock1) } returns withdrawalMock2

        withdrawalExecutor.execute(withdrawalMock1)

        verify { eventsServiceMock.send(withdrawalMock2) }
    }
}