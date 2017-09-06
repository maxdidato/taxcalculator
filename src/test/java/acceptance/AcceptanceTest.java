package acceptance;

import max.taxcalculator.Basket;
import max.taxcalculator.Receipt;
import max.taxcalculator.model.*;
import max.taxcalculator.strategy.TaxCalculator;
import max.taxcalculator.strategy.TaxRule;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.util.Collections.singletonList;
import static max.taxcalculator.model.ItemType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AcceptanceTest {

    private TaxCalculator taxCalculator;

    @Before
    public void setUp() {
        List<TaxRule> rules = new ArrayList();
        rules.add(new TaxRule().withTypesApplied(singletonList(ALL)).withPriority(2).withTaxPercentage(17.5));
        rules.add(new TaxRule().withTypesApplied(singletonList(MEDICAL)).withPriority(1).withTaxPercentage(0));
        rules.add(new TaxRule().withTypesApplied(singletonList(CD)).withPriority(1).withTaxPercentage(17.5).withAdditionalCharge(1.25));
        taxCalculator = new TaxCalculator(rules);
    }

    @Test
    public void it_adds_items_to_the_receipt_and_calculate_the_taxation_adding_a_fix_amount_for_cd() {
        Basket basket = new Basket();
        basket.addItem(1, new Item(BOOK, "A Book", new BigDecimal(29.49)));
        basket.addItem(1, new Item(CD, "Music CD", new BigDecimal(15.99)));
        basket.addItem(1, new Item(FOOD, "Chocolate Snack", new BigDecimal(0.75)));
        Receipt receipt = new Receipt(taxCalculator, basket);
        ReceiptRow receiptRow = new ReceiptRow(1, "A Book",
                new BigDecimal(34.69).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(5.2).setScale(2, ROUND_HALF_EVEN));
        ReceiptRow receiptRow1 = new ReceiptRow(1, "Music CD",
                new BigDecimal(20.04).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(4.05).setScale(2, ROUND_HALF_EVEN));
        ReceiptRow receiptRow2 = new ReceiptRow(1, "Chocolate Snack",
                new BigDecimal(0.90).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(0.15).setScale(2, ROUND_HALF_EVEN));
        receipt.generate();
        assertThat(receipt.getItems(), is(Arrays.asList(receiptRow, receiptRow1, receiptRow2)));
        assertThat(receipt.getTotalTaxes(), is(new BigDecimal(9.40).setScale(2, ROUND_HALF_EVEN)));
        assertThat(receipt.getTotal(), is(new BigDecimal(55.63).setScale(2, ROUND_HALF_EVEN)));


    }

    @Test
    public void it_adds_items_to_the_receipt_and_calculate_the_taxation_applying_an_examption_for_medical_items() {
        Basket basket = new Basket();
        basket.addItem(1, new Item(OFF_LICENCE, "Wine", new BigDecimal(20.99)));
        basket.addItem(1, new Item(MEDICAL, "Pills for headache", new BigDecimal(4.15)));
        basket.addItem(1, new Item(GENERAL, "Box of pins", new BigDecimal(11.25)));
        basket.addItem(1, new Item(CD, "A CD", new BigDecimal(14.99)));
        Receipt receipt = new Receipt(taxCalculator, basket);
        ReceiptRow receiptRow = new ReceiptRow(1, "Wine",
                new BigDecimal(24.69).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(3.70).setScale(2, ROUND_HALF_EVEN));
        ReceiptRow receiptRow1 = new ReceiptRow(1, "Pills for headache",
                new BigDecimal(4.15).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(0).setScale(2, ROUND_HALF_EVEN));
        ReceiptRow receiptRow2 = new ReceiptRow(1, "Box of pins",
                new BigDecimal(13.25).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(2.00).setScale(2, ROUND_HALF_EVEN));
        ReceiptRow receiptRow3 = new ReceiptRow(1, "A CD",
                new BigDecimal(18.89).setScale(2, ROUND_HALF_EVEN),
                new BigDecimal(3.90).setScale(2, ROUND_HALF_EVEN));
        receipt.generate();
        assertThat(receipt.getItems(), is(Arrays.asList(receiptRow, receiptRow1, receiptRow2,receiptRow3)));
        assertThat(receipt.getTotalTaxes(), is(new BigDecimal(9.60).setScale(2, ROUND_HALF_EVEN)));
        assertThat(receipt.getTotal(), is(new BigDecimal(60.98).setScale(2, ROUND_HALF_EVEN)));




    }
}
