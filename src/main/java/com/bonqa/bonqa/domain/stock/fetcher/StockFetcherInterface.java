package com.bonqa.bonqa.domain.stock.fetcher;
import com.bonqa.bonqa.domain.model.data.StockGeneric;

import java.util.List;

public interface StockFetcherInterface {
    List<StockGeneric> fetchStocksFromAPI();
}

