package com.example.economic.dto;

import java.util.List;

public record CustomerInvoiceBookedCollectionResponse(
    List<InvoiceBooked> collection,
    Object metaData,
    Pagination pagination,
    String self
) {

    public record Pagination(
        Integer currentPage,
        Integer totalPages,
        Integer pageSize,
        Long totalCount,
        String self,
        String next,
        String previous
    ) {}
}