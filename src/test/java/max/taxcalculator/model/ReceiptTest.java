package max.taxcalculator.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static max.taxcalculator.model.ItemType.*;
import static org.junit.Assert.*;

public class ReceiptTest {

    @Test
    public void it_creates_a_receipt_entry_from_a_basket() {
        Basket basket = new Basket();
        basket.addItem(2, new Item(GENERAL, "Item", new BigDecimal(10.4)));
        basket.addItem(2, new Item(OFF_LICENCE, "Item1", new BigDecimal(10.4)));
        basket.addItem(2, new Item(BOOK, "Item2", new BigDecimal(10.4)));
        Receipt receipt = new Receipt();
        receipt.calculate(basket);
        assertThat(receipt.getItems(), is(Arrays.asList(
                new ReceiptRow().withDescription("Item").withQuantity(2),
                new ReceiptRow().withDescription("Item1").withQuantity(2),
                new ReceiptRow().withDescription("Item2").withQuantity(2))));



    }

}