package unit;

import max.taxcalculator.model.Item;
import max.taxcalculator.model.ItemType;
import max.taxcalculator.strategy.TaxRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TaxRuleTest {

    @Test
    public void it_calculate_the_tax_percentage_of_an_item_if_the_category_applies() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(10).withTypesApplied(singletonList(CD));
        Item item = new Item().withType(CD).withPrice(new BigDecimal(120));
        assertThat(taxRule.calculate(item), is(Optional.of(new BigDecimal(12))));
    }

    @Test
    public void if_type_applied_containes_all_it_applies_to_all_items() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(10).withTypesApplied(singletonList(ALL));
        Arrays.stream(ItemType.values()).forEach(type -> {
            Item item = new Item().withType(type).withPrice(new BigDecimal(120));
            assertThat(String.format("The rule ALL didnt applie to itemtype %s", type),
                    taxRule.calculate(item), is(Optional.of(new BigDecimal(12))));

        });
    }

    @Test
    public void it_calculates_the_additional_amount_of_an_item_if_the_category_applies() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(10).withAdditionalCharge(2).withTypesApplied(singletonList(CD));
        Item item = new Item().withType(CD).withPrice(new BigDecimal(120));
        assertThat(taxRule.calculate(item), is(Optional.of(new BigDecimal(14))));
    }

    @Test
    public void if_the_additional_charge_is_0_nothing_is_added() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(10).withTypesApplied(singletonList(CD)).withAdditionalCharge(0);
        Item item = new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(Optional.of(new BigDecimal(10))));
    }

    @Test
    public void if_tax_amount_is_0_then_0_is_returned() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(0).withTypesApplied(singletonList(CD));
        Item item = new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(Optional.of(new BigDecimal(0))));
    }

    @Test
    public void if_the_item_doesnt_match_any_rule_it_returns_empty() {
        TaxRule taxRule = new TaxRule().withTaxPercentage(10).withTypesApplied(singletonList(CD));
        Item item = new Item().withDescription("A CD").withType(GENERAL).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(Optional.empty()));
    }

}