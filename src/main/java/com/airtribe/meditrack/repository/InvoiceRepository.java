package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.util.DataStore;

public class InvoiceRepository {
	
    private static DataStore<Bill> invoiceStore = new DataStore<>();


    public Bill getInvoice(String invoiceNumber) {
        return invoiceStore.get(invoiceNumber);
    }
    
    public void saveInvoice(Bill bill) {
         invoiceStore.add(bill.getInvoiceNumber(), bill);
    }

 
}
