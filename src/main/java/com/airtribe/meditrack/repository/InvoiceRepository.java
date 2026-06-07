package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.bill.service.BillSummary;
import com.airtribe.meditrack.util.DataStore;

public class InvoiceRepository {
	
    private static DataStore<BillSummary> invoiceStore = new DataStore<>();


    public BillSummary getInvoice(String invoiceNumber) {
        return invoiceStore.get(invoiceNumber);
    }
    
    public void saveInvoice(BillSummary bill) {
         invoiceStore.add(bill.getInvoiceNumber(), bill);
    }

 
}
