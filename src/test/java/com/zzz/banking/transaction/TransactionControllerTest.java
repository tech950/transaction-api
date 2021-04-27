package com.zzz.banking.transaction;

import com.zzz.banking.account.Account;
import com.zzz.banking.transaction.service.ITransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TransactionController.class)
public class TransactionControllerTest {

    private static final String BASE_URL = "/transaction";

    @MockBean
    private ITransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTransaction() throws Exception {
        String request = "{\n" +
                "  \"sourceAccountId\": 5555,\n" +
                "  \"targetAccountId\": 4444,\n" +
                "  \"currency\": \"GBP\",\n" +
                "  \"amount\": 100.00\n" +
                "}";
        Date date = Date.from(LocalDateTime.of(2021, 10, 9, 6, 30)
                .atZone(ZoneId.systemDefault()).toInstant());
        when(transactionService.transferAmount(any()))
                .thenReturn(new TransactionResult(Long.valueOf(11), new Account(Long.valueOf(5555),
                        BigDecimal.valueOf(3000.00), "GBP", date), new Account(Long.valueOf(4444),
                        BigDecimal.valueOf(6000.00), "GBP", date)));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());
    }

}
