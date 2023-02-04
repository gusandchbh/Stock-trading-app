package com.bonqa.bonqa.service;
import com.bonqa.bonqa.model.ApiStock;

import java.util.List;

public interface StockFetcherInterface {
    List<ApiStock> fetchStocksFromAPI();
}

