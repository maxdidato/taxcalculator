package max.taxcalculator.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import max.taxcalculator.model.BasketItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static max.taxcalculator.utilities.CurrencyUtils.bigDecimalScaleTwo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxCalculator implements Calculator {
    /*This implementation of Calculator takes a list of rule to apply to a basketItem to calculate the taxes
         For the implementation of this test the rules are fed to the system by manually creating them in the acceptance tests.
         Ideally the information for the rules can be stored in a config file or a database so that they can be changed without
         a redeploy. The logic applies the first rule that match the item type. So only one rule is executed. In this way
         is easy to override a generic rule (17.5 taxes) with something more specific for an item, like cd or medic.
         It can be easily changed (by changing Calculation implementation) to apply all the rules that match a given item
         by adding up all the partial results, so for example to add additional behaviour on a given rule
         */
    List<TaxRule> taxRuleList;
    public static final double ROUNDING_FACTOR = 0.05;

    public BigDecimal calculate(BasketItem basketItem) {
        taxRuleList.sort(Comparator.comparingInt(a -> a.getPriority()));
        return applyFirstMatchingRule(basketItem);
    }

    private BigDecimal applyFirstMatchingRule(BasketItem item1) {
        for (TaxRule taxRule : taxRuleList) {
            Optional<BigDecimal> taxAmount = taxRule.calculate(item1.getItem());
            if (taxAmount.isPresent()) {
                return roundToNextHalf(taxAmount.get()).multiply(bigDecimalScaleTwo(ROUNDING_FACTOR));
            }
        }
        return new BigDecimal(0);
    }

    private BigDecimal roundToNextHalf(BigDecimal taxAmount) {
        return taxAmount.divide(new BigDecimal(0.05), 0, RoundingMode.UP);
    }
}