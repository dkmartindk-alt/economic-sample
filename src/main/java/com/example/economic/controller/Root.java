package com.example.economic.controller;

import com.example.economic.dto.Customer;
import com.example.economic.dto.CustomerInvoiceBookedCollectionResponse;
import com.example.economic.dto.CustomerInvoiceTotals;
import com.example.economic.service.EconomicApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
public class Root {

    private EconomicApiService economicApiService;

    @Autowired
    public Root(EconomicApiService economicApiService){
        this.economicApiService = economicApiService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<Customer> customers = economicApiService.getCustomers();
        model.addAttribute("customers", customers);
        return "index";
    }

    @RequestMapping("/customers/{customerNumber}")
    public String customerInvoicesByYear(Model model, @PathVariable int customerNumber) {
        Customer customers = economicApiService.getCustomer(customerNumber);
        model.addAttribute("customer", customers);
        CustomerInvoiceTotals customerInvoiceTotals = economicApiService.getCustomerInvoiceTotalsLatest(customerNumber);
        model.addAttribute("customerInvoiceTotals", customerInvoiceTotals);

        CustomerInvoiceBookedCollectionResponse customerInvoiceBookedCollectionResponse = economicApiService.getCustomerInvoicesBooked(customerNumber);
        model.addAttribute("customerInvoiceBookedCollectionResponse", customerInvoiceBookedCollectionResponse);

        return "customers/index";
    }

    @GetMapping("/customers/{customerNumber}/invoices/{invoiceNumber}/pdf")
    @ResponseBody
    public void getPdfInvoice(@PathVariable int customerNumber, @PathVariable int invoiceNumber, HttpServletResponse response) throws IOException {
        // Get the customer's invoice collection
        CustomerInvoiceBookedCollectionResponse collection = economicApiService.getCustomerInvoicesBooked(customerNumber);

        // Find the specific invoice
        var invoice = collection.collection().stream()
            .filter(inv -> inv.bookedInvoiceNumber().equals(invoiceNumber))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if(invoice.pdf() == null || invoice.pdf().download() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "PDF not available for this invoice");
            return;
        }

        // Download the PDF from e-conomic and relay to the client
        byte[] pdfBytes = economicApiService.getPdfFromUrl(invoice.pdf().download());

        response.setContentType("application/pdf");
        response.setContentLength(pdfBytes.length);
        response.getOutputStream().write(pdfBytes);
    }
}

