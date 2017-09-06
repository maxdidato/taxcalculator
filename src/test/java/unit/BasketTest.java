package unit;

import max.taxcalculator.Basket;
import max.taxcalculator.model.BasketItem;
import max.taxcalculator.model.Item;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;

import static max.taxcalculator.model.ItemType.CD;
import static max.taxcalculator.model.ItemType.MEDICAL;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class BasketTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private Basket basket;


    @Before
    public void setUp(){
        basket = new Basket();
    }
    @Test
    public void it_adds_elements_to_the_basket_by_the_quantity(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        basket = new Basket();
        basket.addItem(2,item);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(2,item))));
    }

    @Test
    public void it_adds_multiple_elements_to_the_basket_by_the_quantity(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        Item anotherItem = new Item().withDescription("Another Item").withType(MEDICAL).withPrice(new BigDecimal(10));
        basket.addItem(2,item);
        basket.addItem(3,anotherItem);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(2,item),new BasketItem(3,anotherItem))));
    }

    @Test
    public void when_same_product_is_added_twice_the_quantity_is_updated(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        basket.addItem(2,item);
        basket.addItem(3,item);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(5,item))));
    }

    @Test
    public void it_accepts_fractions_of_quantities(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        basket.addItem(2.5,item);
        basket.addItem(3.5,item);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(6,item))));
    }

    @Test
    public void it_raise_an_exception_if_negative_quantity(){
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Quantity must be a positive number");
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        basket.addItem(-1,item);
    }

    @Test
    public void it_raise_an_exception_if_quantity_is_zero(){
        expectedEx.expect(InvalidParameterException.class);
        expectedEx.expectMessage("Quantity must be a positive number");
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        basket.addItem(0,item);
    }

}