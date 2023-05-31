package com.bonqa.bonqa.integration_tests;

import bonqa.BonqaApplication;
import bonqa.marketstock.MarketStock;
import bonqa.marketstock.MarketStockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/*
@SpringBootTest(classes = BonqaApplication.class)
@AutoConfigureMockMvc
class MarketStockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketStockRepository marketStockRepository;

    @Test
    @WithMockUser(username = "user1")
    void getAllStocks_returnsListOfStocks() throws Exception {
        MarketStock mockStock1 = new MarketStock();
        mockStock1.setId(1L);
        mockStock1.setName("Apple Inc.");

        MarketStock mockStock2 = new MarketStock();
        mockStock2.setId(2L);
        mockStock2.setName("Microsoft Corporation");

        List<MarketStock> mockStocks = Arrays.asList(mockStock1, mockStock2);

        when(marketStockRepository.findAll()).thenReturn(mockStocks);

        mockMvc.perform(get("/api/v1/stocks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(mockStock1.getName())))
                .andExpect(jsonPath("$[1].name", is(mockStock2.getName())));
    }

    @Test
    @WithMockUser(username = "user1")
    void getAllStocks_returnsEmptyListWhenNoData() throws Exception {
        when(marketStockRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/stocks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}*/
