package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.PaymentMethodNotFoundException
import com.wezaam.withdrawal.exception.UserNotFoundException
import com.wezaam.withdrawal.model.PaymentMethod
import com.wezaam.withdrawal.model.User
import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.repository.WithdrawalRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WithdrawalServiceTest {

    val withdrawalRepositoryMock: WithdrawalRepository = mockk()

    val userServiceMock: UserService = mockk()

    val paymentMethodServiceMock: PaymentMethodService = mockk()

    val withdrawalService: WithdrawalService = WithdrawalService(
        withdrawalRepositoryMock,
        userServiceMock,
        paymentMethodServiceMock
    )

    @Test
    fun findAll_whenHavingTwoRecords_thenReturnListOfWithdrawals() {
        val withdrawalMock1: Withdrawal = mockk()
        every { withdrawalMock1.id } returns 5
        val withdrawalMock2: Withdrawal = mockk()
        every { withdrawalRepositoryMock.findAll() } returns listOf(withdrawalMock1, withdrawalMock2)

        val actual = withdrawalService.findAll()

        assertTrue { actual.isNotEmpty() }
        assertEquals(5, actual.get(0).id)
    }

    @Test
    fun create_whenValidWithdrawal_thenReturnSavedInstance() {
        val userMock: User = mockk()
        val paymentMethodMock: PaymentMethod = mockk()
        val withdrawal1Mock: Withdrawal = spyk()
        every { withdrawal1Mock.userId } returns 101
        every { withdrawal1Mock.paymentMethodId } returns 525
        val expected: Withdrawal = mockk()
        every { withdrawal1Mock.status }
        every { withdrawalRepositoryMock.save(withdrawal1Mock) } returns expected
        every { userServiceMock.findById(101) } returns userMock
        every { paymentMethodServiceMock.findById(525) } returns paymentMethodMock

        val actual = withdrawalService.create(withdrawal1Mock)

        assertEquals(expected, actual)
    }

    @Test
    fun create_whenWrongUserId_thenThrowException() {
        val withdrawal1Mock: Withdrawal = mockk()
        every { withdrawal1Mock.userId } returns 101
        every { userServiceMock.findById(101) } throws UserNotFoundException("101")

        assertThrows<UserNotFoundException> {
            withdrawalService.create(withdrawal1Mock)
        }
    }

    @Test
    fun create_whenWrongPaymentMethodId_thenThrowException() {
        val userMock: User = mockk()
        val withdrawal1Mock: Withdrawal = mockk()
        every { withdrawal1Mock.userId } returns 101
        every { withdrawal1Mock.paymentMethodId } returns 535
        every { userServiceMock.findById(101) } returns userMock
        every { paymentMethodServiceMock.findById(535) } throws PaymentMethodNotFoundException("535")

        assertThrows<PaymentMethodNotFoundException> {
            withdrawalService.create(withdrawal1Mock)
        }
    }

    @Test
    fun save_whenValidWithdrawal_thenReturnSavedInstance() {
        val withdrawal1Mock: Withdrawal = mockk()
        val expected: Withdrawal = mockk()
        every { withdrawalRepositoryMock.save(withdrawal1Mock) } returns expected

        val actual = withdrawalService.save(withdrawal1Mock)

        assertEquals(expected, actual)
    }

    @Test
    fun findAllByExecutedAtBeforeNow_whenEmptyList_thenReturnEmptyList() {
        every { withdrawalRepositoryMock.findAllByExecuteAtBefore(any()) } returns listOf()

        val actual = withdrawalService.findAllByExecuteAtBeforeNow()

        assertTrue { actual.isEmpty() }
    }


}