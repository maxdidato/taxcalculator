package max.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxCalculator implements Calculator {

    List<TaxRule> taxRuleList;

    public BigDecimal calculate(BasketItem basketItem) {
        taxRuleList.sort(Comparator.comparingInt(a -> a.getPriority()));
        return applyFirstMatchingRule(basketItem);
    }

    private BigDecimal applyFirstMatchingRule(BasketItem item1) {
        for (TaxRule taxRule : taxRuleList) {
            Optional<BigDecimal> taxAmount = taxRule.calculate(item1.getItem());
            if (taxAmount.isPresent()) {
                return roundToNextHalf(taxAmount.get()).multiply(new BigDecimal(0.05)).setScale(2, ROUND_HALF_EVEN);
            }
        }
        return new BigDecimal(0);
    }

    private BigDecimal roundToNextHalf(BigDecimal taxAmount) {
        return taxAmount.divide(new BigDecimal(0.05), 0, RoundingMode.UP);
    }
}