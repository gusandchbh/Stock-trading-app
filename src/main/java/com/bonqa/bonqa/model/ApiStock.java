package com.bonqa.bonqa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiStock {
    private String name;
    private BigDecimal price;
    private String ticker;
    private Long volume;
    private BigDecimal open;
    private BigDecimal close;
}