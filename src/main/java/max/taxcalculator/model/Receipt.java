package max.taxcalculator.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Receipt {

    private final TaxCalculator taxCalculator;
    private List<ReceiptRow> items;
    private double salesTaxes;
    private double total;


    public Receipt(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
        items = new ArrayList<>();
    }

    public void generate(Basket basket) {
        basket.getItems().forEach(i -> {
            taxCalculator.calculate(i);
            items.add(new ReceiptRow()
                    .withDescription(i.getItem().getDescription())
                    .withQuantity(i.getQuantity()));

        });
    }
}
