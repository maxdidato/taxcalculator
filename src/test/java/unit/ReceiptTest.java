package unit;

import max.taxcalculator.Basket;
import max.taxcalculator.model.BasketItem;
import max.taxcalculator.model.Item;
import max.taxcalculator.Receipt;
import max.taxcalculator.strategy.TaxCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static max.taxcalculator.utilities.CurrencyUtils.bigDecimalScaleTwo;
import static org.hamcrest.Matchers.is;
import static max.taxcalculator.model.ItemType.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptTest {

    @Mock
    private TaxCalculator taxCalculator;

    @Test
    public void it_delegates_the_calculation_of_taxes_to_taxcalculator() {
        Basket basket = new Basket();
        int taxAmount = 10;
        when(taxCalculator.calculate(any(BasketItem.class))).thenReturn(new BigDecimal(taxAmount));
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(10.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10.4)));
        Receipt receipt = new Receipt(taxCalculator,basket);
        receipt.generate();
        assertThat(receipt.getItems().get(0).getTaxTotal(), is(bigDecimalScaleTwo(10.00)));
        assertThat(receipt.getItems().get(1).getTaxTotal(), is(bigDecimalScaleTwo(10.00)));
        assertThat(receipt.getItems().get(2).getTaxTotal(), is(bigDecimalScaleTwo(10.00)));
    }

    @Test
    public void it_calculates_the_total_summing_up_quantity_and_taxes() {
        Basket basket = new Basket();
        int taxAmount = 10;
        when(taxCalculator.calculate(any(BasketItem.class))).thenReturn(new BigDecimal(taxAmount));
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(11.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10)));
        Receipt receipt = new Receipt(taxCalculator,basket);
        receipt.generate();
        assertThat(receipt.getItems().get(0).getTotal(), is(bigDecimalScaleTwo(30.80)));
        assertThat(receipt.getItems().get(1).getTotal(), is(bigDecimalScaleTwo(32.80)));
        assertThat(receipt.getItems().get(2).getTotal(), is(bigDecimalScaleTwo(30.00)));
    }

    @Test
    public void it_calculate_the_total_amount_of_the_receipt() {
        Basket basket = new Basket();
        int taxAmount = 10;
        when(taxCalculator.calculate(any(BasketItem.class))).thenReturn(new BigDecimal(taxAmount));
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(11.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10)));
        Receipt receipt = new Receipt(taxCalculator,basket);
        receipt.generate();
        assertThat(receipt.getTotal(),is(bigDecimalScaleTwo(93.60)));
    }

    @Test
    public void it_calculate_the_total_amount_of_the_taxes() {
        Basket basket = new Basket();
        int taxAmount = 10;
        when(taxCalculator.calculate(any(BasketItem.class))).thenReturn(new BigDecimal(taxAmount));
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(11.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10)));
        Receipt receipt = new Receipt(taxCalculator,basket);
        receipt.generate();
        assertThat(receipt.getTotalTaxes(),is(bigDecimalScaleTwo(30.00)));
    }
}