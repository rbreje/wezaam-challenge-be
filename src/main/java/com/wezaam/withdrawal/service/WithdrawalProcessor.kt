package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.TransactionException
import com.wezaam.withdrawal.model.Withdrawal
import com.wezaam.withdrawal.model.WithdrawalStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Component
class WithdrawalProcessor(
        private val withdrawalService: WithdrawalService,
        private val paymentMethodService: PaymentMethodService,
        private val withdrawalProcessingService: WithdrawalProcessingService,
        private val eventsService: EventsService
) {

    private val executorService: ExecutorService = Executors.newCachedThreadPool();

    @Scheduled(fixedDelay = 5000)
    fun run() {
        withdrawalService.findAllByExecuteAtBeforeNow().forEach(this::process)
    }

    fun process(withdrawal: Withdrawal) {
        executorService.submit {
            try {
                val paymentMethod = paymentMethodService.findById(withdrawal.paymentMethodId)
                val transactionId: Long = withdrawalProcessingService.sendToProcessing(withdrawal.amount, paymentMethod)
                withdrawal.status = WithdrawalStatus.PROCESSING
                withdrawal.transactionId = transactionId
            } catch (e: Exception) {
                if (e is TransactionException) withdrawal.status = WithdrawalStatus.FAILED else withdrawal.status = WithdrawalStatus.INTERNAL_ERROR
            } finally {
                val savedWithdrawal = withdrawalService.create(withdrawal)
                eventsService.send(savedWithdrawal)
            }
        }
    }
}