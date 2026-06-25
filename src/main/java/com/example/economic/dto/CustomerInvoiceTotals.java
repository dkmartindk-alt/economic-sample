package com.example.economic.dto;

import java.math.BigDecimal;

public record CustomerInvoiceTotals(
    BigDecimal netAmountInBaseCurrency,
    Integer invoiceCount,
    String maxDate,
    String minDate
){}