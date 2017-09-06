package max.taxcalculator.model;

import org.junit.Test;
import org.mockito.InOrder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_FLOOR;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.BOOK;
import static max.taxcalculator.model.ItemType.CD;
import static max.taxcalculator.model.ItemType.GENERAL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;

public class TaxCalculatorTest {

    @Test
    public void it_applies_a_rule_to_an_item_to_calculate_the_tax_total_and_returns_it() {
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(10.00).setScale(2,ROUND_HALF_EVEN)));
    }

    @Test
    public void it_adds_the_additional_amount_of_the_rule_to_the_item_if_present(){
        TaxRule taxRule = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(CD)).withAdditional_charge(2);
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(12.00).setScale(2,ROUND_HALF_EVEN)));
    }

    @Test
    public void it_rounds_the_amount_to_the_next_0_5(){
        TaxRule taxRule = new TaxRule().withTax_percentage(17.5).withTypesApplied(singletonList(BOOK));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A Book").withType(BOOK).withPrice(new BigDecimal(29.49)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(5.20).setScale(2, ROUND_FLOOR)));
    }

    @Test
    public void if_multiple_rules_are_applied_to_the_same_item_the_one_with_higher_rank_priority_wins(){
        TaxRule taxRuleToBeApplied = new TaxRule().withTax_percentage(10).withAdditional_charge(2).
                withTypesApplied(singletonList(CD)).withPriority(1);
        TaxRule taxRuleLowRankPriority = new TaxRule().withTax_percentage(15).withTypesApplied(singletonList(CD)).withPriority(2);
        TaxCalculator taxCalculator = new TaxCalculator(asList(taxRuleToBeApplied,taxRuleLowRankPriority));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(12.00).setScale(2,ROUND_HALF_EVEN)));
    }

    @Test
    public void when_a_rule_doesnt_belong_to_an_item_it_gets_skipped(){
        TaxRule taxRule1 = new TaxRule().withTax_percentage(10).withAdditional_charge(2).withTypesApplied(singletonList(CD));
        TaxRule taxRule2 = new TaxRule().withTax_percentage(10).withTypesApplied(singletonList(GENERAL));
        TaxCalculator taxCalculator = new TaxCalculator(asList(taxRule1,taxRule2));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(100)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(12.00).setScale(2,ROUND_HALF_EVEN)));
    }

    @Test
    public void it_scales_the_amount_to_2_digits_and_rounding_half_up(){
        TaxRule taxRule1 = new TaxRule().withTax_percentage(17.5).withTypesApplied(singletonList(CD));
        TaxCalculator taxCalculator = new TaxCalculator(singletonList(taxRule1));
        BasketItem basketItem = new BasketItem().withQuantity(2).withItem(
                new Item().withDescription("A CD").withType(CD).withPrice(new BigDecimal(14.99)));
        assertThat(taxCalculator.calculate(basketItem), is(new BigDecimal(2.65).setScale(2,ROUND_HALF_EVEN)));
    }


    @Test
    public void rules_are_applied_following_the_priority(){
        TaxRule taxRule1 = spy(new TaxRule().withPriority(2).withTypesApplied(singletonList(GENERAL)));
        TaxRule taxRule2 = spy(new TaxRule().withPriority(3).withTypesApplied(singletonList(CD)));
        TaxRule taxRule3 = spy(new TaxRule().withPriority(1).withTypesApplied(singletonList(GENERAL)));


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