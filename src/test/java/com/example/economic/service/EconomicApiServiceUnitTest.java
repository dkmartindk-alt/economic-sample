package com.example.economic.service;

import com.example.economic.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EconomicApiServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EconomicApiService economicApiService;

    private final String baseUrl = "https://restapi.e-conomic.com";

    @BeforeEach
    void setUp() {
        economicApiService.setApiBaseUrl(baseUrl);
        economicApiService.setAppSecretToken("demo");
        economicApiService.setAgreementGrantToken("demo");
    }

    // ==========================================
    // TESTS FOR: getCustomers()
    // ==========================================

    @Test
    void getCustomers_Success_ReturnsList() {
        // Arrange
        Customer mockCustomer = new Customer("Test Customer", 101, "Street", BigDecimal.ZERO, false, "City", null, "", "", "Denmark", null, "DKK", null, BigDecimal.ZERO, null, null, null, null, "", "", null, null, null, null, "", "", "", null, null, null, null, null, null, null, null, "");
        CustomerCollectionResponse mockResponse = new CustomerCollectionResponse(List.of(mockCustomer));

        when(restTemplate.exchange(
                eq(baseUrl + "/customers"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CustomerCollectionResponse.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Act
        List<Customer> result = economicApiService.getCustomers();

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Test Customer");
        assertThat(result.get(0).customerNumber()).isEqualTo(101);
    }

    @Test
    void getCustomers_ApiError_ThrowsRuntimeException() {
        // Arrange
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CustomerCollectionResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act & Assert
        assertThatThrownBy(() -> economicApiService.getCustomers())
                .isInstanceOf(RuntimeException.class);
    }

    // ==========================================
    // TESTS FOR: getCustomerInvoiceTotalsLatest()
    // ==========================================

    @Test
    void getCustomerInvoiceTotalsLatest_Success() {
        // Arrange
        int customerNo = 101;
        CustomerInvoiceTotals mockTotals = new CustomerInvoiceTotals(new BigDecimal("500.00"), 2, "2026-01-01", "2026-06-25");

        when(restTemplate.exchange(
                eq(baseUrl + "/invoices/totals/booked/customers/" + customerNo),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CustomerInvoiceTotals.class)
        )).thenReturn(new ResponseEntity<>(mockTotals, HttpStatus.OK));

        // Act
        CustomerInvoiceTotals result = economicApiService.getCustomerInvoiceTotalsLatest(customerNo);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.netAmountInBaseCurrency()).isEqualTo(new BigDecimal("500.00"));
        assertThat(result.invoiceCount()).isEqualTo(2);
    }

    @Test
    void getCustomerInvoiceTotalsLatest_ApiError_ThrowsRuntimeException() {
        // Arrange
        int customerNo = 101;
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CustomerInvoiceTotals.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThatThrownBy(() -> economicApiService.getCustomerInvoiceTotalsLatest(customerNo))
                .isInstanceOf(RuntimeException.class);
    }

    // ==========================================
    // TESTS FOR: getCustomer()
    // ==========================================

    @Test
    void getCustomer_Success() {
        // Arrange
        int customerNo = 101;
        Customer mockCustomer = new Customer("Test Customer", customerNo, "Street", BigDecimal.ZERO, false, "City", null, "", "", "Denmark", null, "DKK", null, BigDecimal.ZERO, null, null, null, null, "", "", null, null, null, null, "", "", "", null, null, null, null, null, null, null, null, "");

        when(restTemplate.exchange(
                eq(baseUrl + "/customers/" + customerNo),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Customer.class)
        )).thenReturn(new ResponseEntity<>(mockCustomer, HttpStatus.OK));

        // Act
        Customer result = economicApiService.getCustomer(customerNo);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.customerNumber()).isEqualTo(customerNo);
        assertThat(result.name()).isEqualTo("Test Customer");
    }

    @Test
    void getCustomer_ApiError_ThrowsRuntimeException() {
        // Arrange
        int customerNo = 999;
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Customer.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        // Act & Assert
        assertThatThrownBy(() -> economicApiService.getCustomer(customerNo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to retrieve customer");
    }

    // ==========================================
    // TESTS FOR: getCustomerInvoicesBooked()
    // ==========================================

    @Test
    void getCustomerInvoicesBooked_Success() {
        // Arrange
        int customerNo = 101;
        InvoiceBooked mockInvoice = new InvoiceBooked(1001, LocalDate.now(), "DKK", BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.TEN, LocalDate.now(), null, null, null, null, null, null, null, null, null, null, List.of(), "sent", "/self");
        CustomerInvoiceBookedCollectionResponse mockResponse = new CustomerInvoiceBookedCollectionResponse(List.of(mockInvoice), null, null, "");

        when(restTemplate.exchange(
                eq(baseUrl + "/customers/" + customerNo + "/invoices/booked"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CustomerInvoiceBookedCollectionResponse.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Act
        CustomerInvoiceBookedCollectionResponse result = economicApiService.getCustomerInvoicesBooked(customerNo);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.collection()).hasSize(1);
        assertThat(result.collection().get(0).bookedInvoiceNumber()).isEqualTo(1001);
    }

    @Test
    void getCustomerInvoicesBooked_ApiError_ThrowsRuntimeException() {
        // Arrange
        int customerNo = 101;
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CustomerInvoiceBookedCollectionResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // Act & Assert
        assertThatThrownBy(() -> economicApiService.getCustomerInvoicesBooked(customerNo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to retrieve customer invoices");
    }

    // ==========================================
    // TESTS FOR: getPdfFromUrl()
    // ==========================================

    @Test
    void getPdfFromUrl_Success_ReturnsBytes() {
        // Arrange
        String downloadUrl = "https://restapi.e-conomic.com/invoices/1001/pdf";
        byte[] expectedPdfBytes = "JVBERi0xLjQKJ...".getBytes(); // Dummy PDF bytes

        when(restTemplate.exchange(
                eq(downloadUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(expectedPdfBytes, HttpStatus.OK));

        // Act
        byte[] result = economicApiService.getPdfFromUrl(downloadUrl);

        // Assert
        assertThat(result).isNotNull().isEqualTo(expectedPdfBytes);
    }

    @Test
    void getPdfFromUrl_ApiError_ThrowsRuntimeException() {
        // Arrange
        String downloadUrl = "https://restapi.e-conomic.com/invoices/1001/pdf";
        when(restTemplate.exchange(eq(downloadUrl), eq(HttpMethod.GET), any(), eq(byte[].class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThatThrownBy(() -> economicApiService.getPdfFromUrl(downloadUrl))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to retrieve PDF");
    }
}