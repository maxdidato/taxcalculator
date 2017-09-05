package max.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class TaxRule {

    private int priority;
    private double tax_percentage;
    private double additional_charge;
    private List<ItemType> typesApplied;


    public BigDecimal calculate(Item item) {
        BigDecimal taxTotal = new BigDecimal(0);
        if (typesApplied.contains(item.getType())) {
            BigDecimal pricePlusTaxes = item.getPrice().multiply(new BigDecimal(tax_percentage)).divide(new BigDecimal(100));
            pricePlusTaxes = pricePlusTaxes.add(new BigDecimal(additional_charge));
            taxTotal = pricePlusTaxes.min(item.getPrice());
        }
        return taxTotal;
    }
}
