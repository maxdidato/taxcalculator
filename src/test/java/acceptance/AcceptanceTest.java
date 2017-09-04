package acceptance;

import org.junit.Test;

import java.util.Arrays;

public class AcceptanceTest {


    @Test
    public void it_adds_items_to_the_receipt_and_calculate_the_taxation_adding_a_fix_amount_for_cd() {
        Receipt receipt = new Receipt();
        receipt.addItem(1, new Item(BOOK, "A Book", 29.49));
        receipt.addItem(1, new Item(CD, "Music CD", 15.99));
        receipt.addItem(2, new Item(FOOD, "Chocolate Snack", 0.75));
        ReceiptRow receiptRow = new ReceiptRow(1, "A Book", 34.69);
        ReceiptRow receiptRow1 = new ReceiptRow(1, "Music CD", 20.04);
        ReceiptRow receiptRow2 = new ReceiptRow(1, "Chocolate Snack", 0.90);
        assertThat(receipt.getItems(), is(Arrays.asList(receiptRow, receiptRow1, receiptRow2)));
        assertThat(receipt.getSalesTaxes(), is(9.40));
        assertThat(receipt.getTotal(), is(55.63));


    }

    @Test
    public void it_adds_items_to_the_receipt_and_calculate_the_taxation_applying_an_examption_for_medical_items() {
        Receipt receipt = new Receipt();
        receipt.addItem(1, new Item(OFF_LICENCE, "Bottle of wine", 20.99));
        receipt.addItem(1, new Item(MEDICAL, "Bottle of headache pills", 4.15));
        receipt.addItem(1, new Item(GENERAL, "Box of pins", 11.25));
        receipt.addItem(1, new Item(CD, "Music CD", 14.99));
        ReceiptRow receiptRow = new ReceiptRow(1, "Bottle of wine", 24.69);
        ReceiptRow receiptRow1 = new ReceiptRow(1, "Bottle of headache pills", 4.15);
        ReceiptRow receiptRow2 = new ReceiptRow(1, "Box of pins", 13.25);
        ReceiptRow receiptRow3 = new ReceiptRow(1, "Music CD", 18.99);
        assertThat(receipt.getItems(), is(Arrays.asList(receiptRow, receiptRow1, receiptRow2,receiptRow3)));
        assertThat(receipt.getSalesTaxes(), is(9.60));
        assertThat(receipt.getTotal(), is(60.98));


    }
}
