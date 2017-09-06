package max.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static max.taxcalculator.model.ItemType.ALL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class TaxRule {

    private int priority;
    private double tax_percentage;
    private double additional_charge;
    private List<ItemType> typesApplied;


    public Optional<BigDecimal> calculate(Item item) {
        BigDecimal taxTotal = null;
        if (typesApplied.contains(ALL) || typesApplied.contains(item.getType())) {
            taxTotal = calculatePricePlusTaxes(item.getPrice()).min(item.getPrice());
        }
        return Optional.ofNullable(taxTotal);
    }

    private BigDecimal calculatePricePlusTaxes(BigDecimal price) {
        BigDecimal pricePlusTaxes = price.multiply(new BigDecimal(tax_percentage)).divide(new BigDecimal(100));
        return pricePlusTaxes.add(new BigDecimal(additional_charge));
    }
}
