package org.hsbc.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction createTransaction(Transaction transaction) {
        if (transactionRepository.existsById(transaction.getId())) {
            throw new IllegalArgumentException("Transaction has existed with id: " + transaction.getId());
        }
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }


    public Page<Transaction> getTransactionsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }

    public Transaction updateTransaction(Transaction transaction) {
        if (!transactionRepository.existsById(transaction.getId())) {
            throw new IllegalArgumentException("Transaction not found with id: " + transaction.getId());
        }
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

}