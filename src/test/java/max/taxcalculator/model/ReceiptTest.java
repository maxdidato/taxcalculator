package max.taxcalculator.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static max.taxcalculator.model.ItemType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptTest {

    @Mock
    private TaxCalculator taxCalculator;

    @InjectMocks
    private Receipt receipt;

    @Test
    public void it_creates_a_receipt_entry_from_a_basket() {
        Basket basket = new Basket();
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(10.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10.4)));
        receipt.generate(basket);
        assertThat(receipt.getItems(), is(Arrays.asList(
                new ReceiptRow().withDescription("Item").withQuantity(2),
                new ReceiptRow().withDescription("Item1").withQuantity(2),
                new ReceiptRow().withDescription("Item2").withQuantity(2))));
    }

    @Test
    public void it_delegates_the_calculation_of_taxes_to_taxcalculator(){
        Basket basket = new Basket();
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(10.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10.4)));
        receipt.generate(basket);
        Mockito.verify(taxCalculator,times(1)).calculate(basket.getItems().get(0));
        Mockito.verify(taxCalculator,times(1)).calculate(basket.getItems().get(1));
        Mockito.verify(taxCalculator,times(1)).calculate(basket.getItems().get(2));
    }




}