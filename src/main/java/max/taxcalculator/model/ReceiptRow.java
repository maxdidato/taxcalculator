package max.taxcalculator.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ReceiptRow {
    private int quantity;
    private String description;
    private double total;
}
