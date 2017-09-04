package max.taxcalculator.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.math.BigDecimal;

@Data
@Wither
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private ItemType type;
    private String description;
    private BigDecimal price;

}
