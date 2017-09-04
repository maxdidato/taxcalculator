package max.taxcalculator.model;


import lombok.Data;

import java.util.List;

@Data
public class Receipt {

    private List<ReceiptRow> items;
    private double salesTaxes;
    private double total;

    public void addItem(int quantity, Item item) {

    }

    public void calculate(Basket basket) {

    }
}
