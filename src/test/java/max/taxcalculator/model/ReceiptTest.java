package max.taxcalculator.model;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.hamcrest.Matchers.is;
import static max.taxcalculator.model.ItemType.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
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
        assertThat(receipt.getItems().get(0).getTaxTotal(), is(new BigDecimal(10.0).setScale(2,ROUND_HALF_EVEN)));
        assertThat(receipt.getItems().get(1).getTaxTotal(), is(new BigDecimal(10.0).setScale(2,ROUND_HALF_EVEN)));
        assertThat(receipt.getItems().get(2).getTaxTotal(), is(new BigDecimal(10.0).setScale(2,ROUND_HALF_EVEN)));
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
        assertThat(receipt.getItems().get(0).getTotal(), is(new BigDecimal(30.8).setScale(2,ROUND_HALF_UP)));
        assertThat(receipt.getItems().get(1).getTotal(), is(new BigDecimal(32.8).setScale(2,ROUND_HALF_EVEN)));
        assertThat(receipt.getItems().get(2).getTotal(), is(new BigDecimal(30.0).setScale(2,ROUND_HALF_EVEN)));
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
        assertThat(receipt.getTotal(),is(new BigDecimal(93.6).setScale(2, ROUND_HALF_EVEN)));
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
        assertThat(receipt.getSalesTaxes(),is(new BigDecimal(30.0).setScale(2, ROUND_HALF_EVEN)));
    }
}