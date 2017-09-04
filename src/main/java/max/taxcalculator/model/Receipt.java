package max.taxcalculator.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Receipt {

    private List<ReceiptRow> items;
    private double salesTaxes;
    private double total;

    public Receipt(){
        items = new ArrayList<>();
    }

    public void calculate(Basket basket) {
        basket.getItems().forEach(i -> items.add(new ReceiptRow()
                .withDescription(i.getItem().getDescription())
                .withQuantity(i.getQuantity())));
    }
}
