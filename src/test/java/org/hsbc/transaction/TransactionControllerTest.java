package org.hsbc.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction(BigDecimal.TEN, "Test", LocalDateTime.now());
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(10));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Long id = 1L;
        Transaction transaction = new Transaction(BigDecimal.TEN, "Test", LocalDateTime.now());
        transaction.setId(id);

        when(transactionService.getTransactionById(id)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }


}