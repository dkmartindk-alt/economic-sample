package com.example.economic.dto;

import java.util.List;

public record CustomerCollectionResponse(
        List<Customer> collection
) {}