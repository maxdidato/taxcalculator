package max.taxcalculator.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import max.taxcalculator.model.Item;
import max.taxcalculator.model.ItemType;

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
    private double taxPercentage;
    private double additionalCharge;
    private List<ItemType> typesApplied;


    public Optional<BigDecimal> calculate(Item item) {
        BigDecimal taxTotal = null;
        if (typesApplied.contains(ALL) || typesApplied.contains(item.getType())) {
            taxTotal = calculatePricePlusTaxes(item.getPrice()).min(item.getPrice());
            taxTotal.setScale(2,BigDecimal.ROUND_HALF_EVEN);
        }
        return Optional.ofNullable(taxTotal);
    }

    private BigDecimal calculatePricePlusTaxes(BigDecimal price) {
        BigDecimal pricePlusTaxes = price.multiply(new BigDecimal(taxPercentage)).divide(new BigDecimal(100));
        return pricePlusTaxes.add(new BigDecimal(additionalCharge));
    }
}
