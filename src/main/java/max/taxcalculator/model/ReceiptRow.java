package max.taxcalculator.model;


import lombok.*;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Wither
@AllArgsConstructor
public class ReceiptRow {

    private double quantity;
    private String description;
    private BigDecimal total;
    private BigDecimal taxTotal;
}
