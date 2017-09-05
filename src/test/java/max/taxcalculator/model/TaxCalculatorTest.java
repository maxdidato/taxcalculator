package max.taxcalculator.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.CD;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TaxCalculatorTest {

    @Test
    public void it_applies_a_rule_to_an_item_to_calculate_the_tax_total_and_returns_it() {
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(10)));
    }
}