package com.bonqa.bonqa.integration_tests;

import static org.hamcrest.Matchers.*;

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
