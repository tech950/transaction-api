package com.zzz.banking.transaction;

import com.zzz.banking.account.dataaccessobject.IAccountCollection;
import com.zzz.banking.account.dataaccessobject.InMemoryAccountCollection;
import com.zzz.banking.transaction.service.ITransactionService;
import com.zzz.banking.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TransactionController.class)
public class TransactionControllerTest {

    private static final String BASE_URL = "/transaction";

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
        String response = "{\n" +
                "  \"id\": 0,\n" +
                "  \"sourceAccount\": {\n" +
                "    \"accountNumber\": 5555,\n" +
                "    \"balance\": 2900,\n" +
                "    \"currency\": \"GBP\",\n" +
                "    \"createdAt\": \"2021-04-08T23:00:00.000+00:00\"\n" +
                "  },\n" +
                "  \"targetAccount\": {\n" +
                "    \"accountNumber\": 4444,\n" +
                "    \"balance\": 6100,\n" +
                "    \"currency\": \"GBP\",\n" +
                "    \"createdAt\": \"2021-04-08T23:00:00.000+00:00\"\n" +
                "  }\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    public void testTransactionNonExistentAccountError() throws Exception {
        String request = "{\n" +
                "  \"sourceAccountId\": 5555,\n" +
                "  \"targetAccountId\": 6666,\n" +
                "  \"currency\": \"GBP\",\n" +
                "  \"amount\": 10000.00\n" +
                "}";
        String expectedResponse = "Source Account 5555 or Target Account 6666 does not exist.";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedResponse)));
    }

    @Test
    public void testTransactionSameAccountError() throws Exception {
        String request = "{\n" +
                "  \"sourceAccountId\": 5555,\n" +
                "  \"targetAccountId\": 5555,\n" +
                "  \"currency\": \"GBP\",\n" +
                "  \"amount\": 100.00\n" +
                "}";
        String expectedResponse = "Source Account 5555 and Target Account 5555 are same.";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedResponse)));
    }

    @Test
    public void testTransactionInsufficientBalanceError() throws Exception {
        String request = "{\n" +
                "  \"sourceAccountId\": 5555,\n" +
                "  \"targetAccountId\": 4444,\n" +
                "  \"currency\": \"GBP\",\n" +
                "  \"amount\": 10000.00\n" +
                "}";
        String expectedResponse = "Source Account 5555 do not have sufficient balance for this transaction.";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isRequestedRangeNotSatisfiable())
                .andExpect(jsonPath("$.message", is(expectedResponse)));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public IAccountCollection getAccountCollection() {
            return new InMemoryAccountCollection();
        }

        @Bean
        @Primary
        public ITransactionService getDelegate() {
            return new TransactionService();
        }
    }
}
