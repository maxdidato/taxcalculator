package max.taxcalculator.model;


import lombok.*;
import lombok.experimental.Wither;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Wither
@AllArgsConstructor
public class ReceiptRow {

    private double quantity;
    private String description;
    private double total;
}
