package com.example.economic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Customer(
        @JsonProperty("name") String name,
        @JsonProperty("customerNumber") Integer customerNumber,
        @JsonProperty("address") String address,
        @JsonProperty("balance") BigDecimal balance,
        @JsonProperty("barred") Boolean barred,
        @JsonProperty("city") String city,
        @JsonProperty("contacts") String contacts,
        @JsonProperty("corporateIdentificationNumber") String corporateIdentificationNumber,
        @JsonProperty("pNumber") String pNumber,
        @JsonProperty("country") String country,
        @JsonProperty("creditLimit") BigDecimal creditLimit,
        @JsonProperty("currency") String currency,
        @JsonProperty("defaultDeliveryLocation") DeliveryLocation defaultDeliveryLocation,
        @JsonProperty("dueAmount") BigDecimal dueAmount,
        @JsonProperty("ean") String ean,
        @JsonProperty("email") String email,
        @JsonProperty("invoices") Invoices invoices,
        @JsonProperty("lastUpdated") LocalDateTime lastUpdated,
        @JsonProperty("publicEntryNumber") String publicEntryNumber,
        @JsonProperty("telephoneAndFaxNumber") String telephoneAndFaxNumber,
        @JsonProperty("mobilePhone") String mobilePhone,
        @JsonProperty("eInvoicingDisabledByDefault") Boolean eInvoicingDisabledByDefault,
        @JsonProperty("templates") Templates templates,
        @JsonProperty("totals") Totals totals,
        @JsonProperty("vatNumber") String vatNumber,
        @JsonProperty("website") String website,
        @JsonProperty("zip") String zip,
        @JsonProperty("attention") Attention attention,
        @JsonProperty("customerContact") CustomerContactObject customerContact,
        @JsonProperty("customerGroup") CustomerGroup customerGroup,
        @JsonProperty("layout") Layout layout,
        @JsonProperty("paymentTerms") PaymentTerms paymentTerms,
        @JsonProperty("salesPerson") SalesPerson salesPerson,
        @JsonProperty("vatZone") VatZone vatZone,
        @JsonProperty("metaData") Object metaData,
        @JsonProperty("self") String self
) {

    public record DeliveryLocation(
            @JsonProperty("deliveryLocationNumber") Integer deliveryLocationNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record Invoices(
            @JsonProperty("drafts") String drafts,
            @JsonProperty("booked") String booked,
            @JsonProperty("self") String self
    ) {
    }

    public record Attention(
            @JsonProperty("customerContactNumber") Integer customerContactNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record CustomerContactObject(
            @JsonProperty("customerContactNumber") Integer customerContactNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record CustomerGroup(
            @JsonProperty("customerGroupNumber") Integer customerGroupNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record Layout(
            @JsonProperty("layoutNumber") Integer layoutNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record PaymentTerms(
            @JsonProperty("paymentTermsNumber") Integer paymentTermsNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record SalesPerson(
            @JsonProperty("employeeNumber") Integer employeeNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record VatZone(
            @JsonProperty("vatZoneNumber") Integer vatZoneNumber,
            @JsonProperty("self") String self
    ) {
    }

    public record Templates(
            @JsonProperty("invoice") String invoice,
            @JsonProperty("invoiceLine") String invoiceLine,
            @JsonProperty("self") String self
    ) {
    }

    public record Totals(
            @JsonProperty("drafts") String drafts,
            @JsonProperty("booked") String booked,
            @JsonProperty("self") String self
    ) {
    }
}
