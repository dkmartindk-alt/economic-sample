package com.example.economic.service;

import com.example.economic.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Starter Spring, så den rigtige RestTemplate og dine ægte @Value tokens indlæses
class EconomicApiServiceIntegrationTest {

    @Autowired
    private EconomicApiService economicApiService; // Den ægte service, klar til brug

    @Test
    void getCustomers_RealApiCall_ReturnsExpectedDemoData() {
        // Act - I am making a real HTTP call to the e-conomic demo API endpoint
        List<Customer> customers = economicApiService.getCustomers();

        // Assert - I want to verify that the overall structure matches the demo environment data
        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(5); // I expect exactly 5 customers from the demo payload

        // Assert - I am checking the first customer in the collection (Customer No. 1)
        Customer firstCustomer = customers.get(0);
        assertThat(firstCustomer.customerNumber()).isEqualTo(1);
        assertThat(firstCustomer.name()).isEqualTo("Decathlon");
        assertThat(firstCustomer.currency()).isEqualTo("DKK");
        assertThat(firstCustomer.country()).isEqualTo("Belgium");
        assertThat(firstCustomer.city()).isEqualTo("Brussels");

        // Assert - I am verifying the fourth customer to ensure Danish characters/data parse correctly (Customer No. 4)
        Customer fourthCustomer = customers.get(3);
        assertThat(fourthCustomer.customerNumber()).isEqualTo(4);
        assertThat(fourthCustomer.name()).isEqualTo("Customer with EAN");
        assertThat(fourthCustomer.country()).isEqualTo("Denmark");
        assertThat(fourthCustomer.city()).isEqualTo("Copenhagen");

        // Assert - I am testing customer No. 3 to see if specific flags match the demo state
        Customer thirdCustomer = customers.get(2);
        assertThat(thirdCustomer.customerNumber()).isEqualTo(3);
        assertThat(thirdCustomer.name()).isEqualTo("Barred customer");
    }

    @Test
    void getCustomerInvoiceTotalsLatest_RealApiCall_ReturnsExpectedInvoiceTotals() {
        // Arrange - I am targetting customer number 1 based on the known demo data payload
        int targetCustomerNumber = 1;

        // Act - I am calling the live endpoint to fetch the latest booked invoice totals
        CustomerInvoiceTotals totals = economicApiService.getCustomerInvoiceTotalsLatest(targetCustomerNumber);

        // Assert - I want to verify that the returned aggregate totals match my exact expectations
        assertThat(totals).isNotNull();
        assertThat(totals.netAmountInBaseCurrency()).isEqualByComparingTo("70.00"); // I expect a net amount of 70.00 in the base currency
        assertThat(totals.invoiceCount()).isEqualTo(1); // I expect exactly 1 booked invoice for this customer

        // Assert - I am verifying the boundary dates for the invoices included in the payload
        // (Note: If your DTO maps these as String, use isEqualTo. If LocalDate, adjust accordingly)
        assertThat(totals.maxDate()).isEqualTo("2022-06-02");
        assertThat(totals.minDate()).isEqualTo("2022-06-02");
    }

    @Test
    void getCustomer_RealApiCall_ReturnsSpecificCustomerDetails() {
        // Arrange - I am targeting customer number 1 to fetch the exact single record for Decathlon
        int targetCustomerNumber = 1;

        // Act - I am calling the live single-customer endpoint
        Customer customer = economicApiService.getCustomer(targetCustomerNumber);

        // Assert - I want to confirm that the individual properties are accurately mapped to my DTO
        assertThat(customer).isNotNull();
        assertThat(customer.customerNumber()).isEqualTo(1);
        assertThat(customer.name()).isEqualTo("Decathlon");
        assertThat(customer.currency()).isEqualTo("DKK");
        assertThat(customer.address()).isEqualTo("Avenue des Arts No 5");
        assertThat(customer.city()).isEqualTo("Brussels");
        assertThat(customer.country()).isEqualTo("Belgium");
        assertThat(customer.email()).isEqualTo("customerone@mailinator.com");

        // Assert - I am checking numeric balances to ensure correct BigDecimals/doubles parsing
        // (Adjust comparison method depending on whether your DTO uses BigDecimal or double)
        assertThat(customer.balance()).isEqualByComparingTo("-1600.00");
        assertThat(customer.dueAmount()).isEqualByComparingTo("0.00");
    }

    @Test
    void getCustomerInvoicesBooked_RealApiCall_ReturnsBookedInvoicesForCustomer() {
        // Arrange - I am targeting customer number 1 to get their history of booked invoices
        int targetCustomerNumber = 1;

        // Act - I am executing the live call to pull down the booked invoice collection response
        CustomerInvoiceBookedCollectionResponse response = economicApiService.getCustomerInvoicesBooked(targetCustomerNumber);

        // Assert - I want to verify that the outer wrapper and collection structure match the demo payload
        assertThat(response).isNotNull();
        assertThat(response.collection()).isNotNull();
        assertThat(response.collection()).hasSize(1); // I expect exactly 1 booked invoice in the array

        // Assert - I am drilling down to verify individual properties of the first booked invoice
        InvoiceBooked firstInvoice = response.collection().get(0);
        assertThat(firstInvoice.bookedInvoiceNumber()).isEqualTo(1);
        assertThat(firstInvoice.currency()).isEqualTo("DKK");
        assertThat(firstInvoice.date()).isEqualTo("2022-06-02"); // (Adjust if your DTO maps this to LocalDate instead of String)

        // Assert - I am checking financial amounts to guarantee that decimal scale mapping functions correctly
        assertThat(firstInvoice.netAmount()).isEqualByComparingTo("70.00");
        assertThat(firstInvoice.grossAmount()).isEqualByComparingTo("87.50");
        assertThat(firstInvoice.vatAmount()).isEqualByComparingTo("17.50");
        assertThat(firstInvoice.remainder()).isEqualByComparingTo("0.00");

        // Assert - I am confirming that nested objects like the PDF download link parsed properly
        assertThat(firstInvoice.pdf()).isNotNull();
        assertThat(firstInvoice.pdf().download()).isEqualTo("https://restapi.e-conomic.com/invoices/booked/1/pdf");
    }

    @Test
    void getPdfFromUrl_RealApiCall_ReturnsValidPdfBytes() {
        // Arrange - I am using the live PDF download URL extracted from invoice 1's demo payload
        String targetPdfUrl = "https://restapi.e-conomic.com/invoices/booked/1/pdf";

        // Act - I am executing the call to stream down the raw binary data from e-conomic
        byte[] pdfBytes = economicApiService.getPdfFromUrl(targetPdfUrl);

        // Assert - I want to ensure that I actually received a populated binary payload back
        assertThat(pdfBytes).isNotNull();
        assertThat(pdfBytes).isNotEmpty();

        // Assert - I am validating that the file size is greater than 1 KB (1024 bytes)
        // to confirm that a real document was fetched, rather than an empty file or a small error string
        int oneKilobyteInBytes = 1024;
        assertThat(pdfBytes.length).isGreaterThan(oneKilobyteInBytes);

        // Assert - I am verifying that the file type is explicitly a PDF by checking its magic bytes.
        // Every valid PDF file must begin with the "%PDF-" header prefix (ASCII values: 0x25, 0x50, 0x44, 0x46, 0x2D)
        String pdfHeaderPrefix = new String(pdfBytes, 0, 5);
        assertThat(pdfHeaderPrefix).isEqualTo("%PDF-");
    }
}