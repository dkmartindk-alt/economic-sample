package com.example.economic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InvoiceBooked(
    Integer bookedInvoiceNumber,
    LocalDate date,
    String currency,
    BigDecimal exchangeRate,
    BigDecimal netAmount,
    BigDecimal netAmountInBaseCurrency,
    BigDecimal grossAmount,
    BigDecimal grossAmountInBaseCurrency,
    BigDecimal vatAmount,
    BigDecimal roundingAmount,
    BigDecimal remainder,
    BigDecimal remainderInBaseCurrency,
    LocalDate dueDate,
    PaymentTerms paymentTerms,
    CustomerRef customer,
    Recipient recipient,
    DeliveryLocation deliveryLocation,
    Delivery delivery,
    Notes notes,
    References references,
    Pdf pdf,
    Layout layout,
    Project project,
    List<Line> lines,
    String sent,
    String self
) {

    public record PaymentTerms(
        Integer paymentTermsNumber,
        Integer daysOfCredit,
        String name,
        String paymentTermsType,
        String self
    ) {}

    public record CustomerRef(
        Integer customerNumber,
        String self
    ) {}

    public record Recipient(
        String name,
        String address,
        String zip,
        String city,
        String country,
        String ean,
        String publicEntryNumber,
        Attention attention,
        VatZone vatZone,
        String cvr
    ) {

        public record Attention(
            Integer customerContactNumber,
            String self
        ) {}

        public record VatZone(
            Integer vatZoneNumber,
            String self
        ) {}
    }

    public record DeliveryLocation(
        Integer deliveryLocationNumber,
        String self
    ) {}

    public record Delivery(
        String address,
        String zip,
        String city,
        String country,
        String deliveryTerms,
        LocalDate deliveryDate
    ) {}

    public record Notes(
        String heading,
        String textLine1,
        String textLine2
    ) {}

    public record References(
        CustomerContact customerContact,
        SalesPerson salesPerson,
        VendorReference vendorReference,
        String other
    ) {

        public record CustomerContact(
            Integer customerContactNumber,
            CustomerRef customer,
            String self
        ) {}

        public record SalesPerson(
            Integer employeeNumber,
            String self
        ) {}

        public record VendorReference(
            Integer employeeNumber,
            String self
        ) {}
    }

    public record Pdf(
        String download
    ) {}

    public record Layout(
        Integer layoutNumber,
        String self
    ) {}

    public record Project(
        Integer projectNumber,
        String self
    ) {}

    public record Line(
        Integer lineNumber,
        Integer sortKey,
        String description,
        LocalDate deliveryDate,
        BigDecimal quantity,
        BigDecimal unitNetPrice,
        BigDecimal discountPercentage,
        BigDecimal unitCostPrice,
        BigDecimal vatRate,
        BigDecimal vatAmount,
        BigDecimal totalNetAmount,
        Unit unit,
        Product product,
        DepartmentalDistribution departmentalDistribution
    ) {

        public record Unit(
            Integer unitNumber,
            String name,
            String self
        ) {}

        public record Product(
            String productNumber,
            String self
        ) {}

        public record DepartmentalDistribution(
            Integer departmentalDistributionNumber,
            String self
        ) {}
    }
}
