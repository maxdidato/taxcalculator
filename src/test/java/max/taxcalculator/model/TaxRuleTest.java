package max.taxcalculator.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.CD;
import static max.taxcalculator.model.ItemType.GENERAL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TaxRuleTest {

    @Test
    public void it_calculate_the_tax_percentage_of_an_item_if_the_category_applies(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        Item item = new Item().withType(CD).withPrice(new BigDecimal(120));
        assertThat(taxRule.calculate(item),is(new BigDecimal(12)));
    }

    @Test
    public void it_calculates_the_additional_amount_of_an_item_if_the_category_applies(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withAdditional_charge(2).withTypesApplied(singletonList(CD));
        Item item = new Item().withType(CD).withPrice(new BigDecimal(120));
        assertThat(taxRule.calculate(item),is(new BigDecimal(14)));
    }

    @Test
    public void if_the_additional_charge_is_0_nothing_is_added(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD)).withAdditional_charge(0);
        Item item = new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(new BigDecimal(10)));
    }

    @Test
    public void if_tax_amount_is_0_then_0_is_returned(){
        TaxRule taxRule = new TaxRule().withTax_percentage(0).withTypesApplied(singletonList(CD));
        Item item = new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(new BigDecimal(0)));
    }

    @Test
    public void if_the_item_doesnt_match_any_rule_it_returns_0(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        Item item = new Item().withDescription("A CD").withType(GENERAL).withPrice(new BigDecimal(100));
        assertThat(taxRule.calculate(item), is(new BigDecimal(0)));
    }

}