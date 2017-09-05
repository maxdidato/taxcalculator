package max.taxcalculator.model;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.internal.InOrderImpl;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.CD;
import static max.taxcalculator.model.ItemType.GENERAL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class TaxCalculatorTest {

    @Test
    public void it_applies_a_rule_to_an_item_to_calculate_the_tax_total_and_returns_it() {
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(10)));
    }

    @Test
    public void it_adds_the_additional_amount_of_the_rule_to_the_item_if_present(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD)).withAdditional_charge(2);
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(12)));
    }

    @Test
    public void if_the_item_doesnt_match_any_rule_it_returns_0(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(GENERAL).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(0)));
    }

    @Test
    public void if_the_additional_charge_is_0_nothing_is_added(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD)).withAdditional_charge(0);
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(10)));
    }

    @Test
    public void if_tax_amount_is_0_then_0_is_returned(){
        TaxRule taxRule = new TaxRule().withTax_percentage(0).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(0)));
    }

    @Test
    public void multiple_rules_can_be_applied_to_the_same_item(){
        TaxRule taxRule1 = new TaxRule().withTax_percentage(10).withAdditional_charge(2).withTypesApplied(singletonList(CD));
        TaxRule taxRule2 = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(asList(taxRule1,taxRule2));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(22)));
    }

    @Test
    public void when_a_rule_doesnt_belong_to_an_item_it_gets_skipped(){
        TaxRule taxRule1 = new TaxRule().withTax_percentage(10).withAdditional_charge(2).withTypesApplied(singletonList(CD));
        TaxRule taxRule2 = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(GENERAL));
        TaxCalculator taxCalculator = new TaxCalculator(asList(taxRule1,taxRule2));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(12)));
    }

    @Test
    public void rules_are_applied_following_the_priority(){
        TaxRule taxRule1 = spy(new TaxRule().withPriority(2).withTypesApplied(singletonList(CD)));
        TaxRule taxRule2 = spy(new TaxRule().withPriority(3).withTypesApplied(singletonList(CD)));
        TaxRule taxRule3 = spy(new TaxRule().withPriority(1).withTypesApplied(singletonList(CD)));


        TaxCalculator taxCalculator = new TaxCalculator(asList(taxRule1,taxRule2,taxRule3));
        Item item = new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                item);

        InOrder rulesExecutionOrder = inOrder(taxRule1, taxRule2,taxRule3);

        taxCalculator.calculate(basketItem);
        rulesExecutionOrder.verify(taxRule3).calculate(item);
        rulesExecutionOrder.verify(taxRule1).calculate(item);
        rulesExecutionOrder.verify(taxRule2).calculate(item);
    }
}