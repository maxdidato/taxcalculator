package max.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxCalculator {

    List<TaxRule> taxRuleList;

    public BigDecimal calculate(BasketItem item1) {
        taxRuleList.sort(Comparator.comparingInt(a -> a.getPriority()));
        return new BigDecimal(taxRuleList.stream().mapToDouble(
                taxRule -> taxRule.calculate(item1.getItem()).doubleValue()).sum());
    }
}
