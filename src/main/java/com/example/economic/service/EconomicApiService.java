package com.example.economic.service;

import com.example.economic.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class EconomicApiService {

    RestTemplate restTemplate;

    @Value("${economic.api.base-url}")
    private String apiBaseUrl;

    @Value("${economic.api.app-secret-token}")
    private String appSecretToken;

    @Value("${economic.api.agreement-grant-token}")
    private String agreementGrantToken;

    public EconomicApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Getters for testing purposes
    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public void setAppSecretToken(String appSecretToken) {
        this.appSecretToken = appSecretToken;
    }

    public void setAgreementGrantToken(String agreementGrantToken) {
        this.agreementGrantToken = agreementGrantToken;
    }


    public List<Customer> getCustomers() {
        ResponseEntity<CustomerCollectionResponse> response = restTemplate.exchange(
                apiBaseUrl + "/customers",
            HttpMethod.GET,
                getDemoHTTPHeader(),
                CustomerCollectionResponse.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException();
        }
        // Return the nested list, or an empty list if the body/collection is null
        CustomerCollectionResponse body = response.getBody();
        if (body != null) {
            return body.collection();
    }
        return List.of();
    }

    public CustomerInvoiceTotals getCustomerInvoiceTotalsLatest(int customerNumber){

        ResponseEntity<CustomerInvoiceTotals> response = restTemplate.exchange(
                apiBaseUrl + "/invoices/totals/booked/customers/" + customerNumber,
                HttpMethod.GET,
                getDemoHTTPHeader(),
                CustomerInvoiceTotals.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException();
        }
        return response.getBody();
    }

    public Customer getCustomer(int customerNumber) {
        ResponseEntity<Customer> response = restTemplate.exchange(
            apiBaseUrl + "/customers/" + customerNumber,
            HttpMethod.GET,
            getDemoHTTPHeader(),
            Customer.class
        );

        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Failed to retrieve customer: " + response.getStatusCode());
        }
        return response.getBody();
    }

    public CustomerInvoiceBookedCollectionResponse getCustomerInvoicesBooked(int customerNumber) {
        ResponseEntity<CustomerInvoiceBookedCollectionResponse> response = restTemplate.exchange(
            apiBaseUrl + "/customers/" + customerNumber + "/invoices/booked",
            HttpMethod.GET,
            getDemoHTTPHeader(),
            CustomerInvoiceBookedCollectionResponse.class
        );

        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Failed to retrieve customer invoices: " + response.getStatusCode());
        }
        return response.getBody();
    }

    public byte[] getPdfFromUrl(String pdfDownloadUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AppSecretToken", appSecretToken);
        headers.set("X-AgreementGrantToken", agreementGrantToken);
        // No Content-Type needed for GET request without body

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
            pdfDownloadUrl,
            HttpMethod.GET,
            entity,
            byte[].class
        );

        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Failed to retrieve PDF: " + response.getStatusCode());
        }
        return response.getBody();
    }





    private HttpEntity getDemoHTTPHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AppSecretToken", appSecretToken);
        headers.set("X-AgreementGrantToken", agreementGrantToken);
        headers.set("Content-Type", "application/json");

        return new HttpEntity<>(headers);
    }
}
