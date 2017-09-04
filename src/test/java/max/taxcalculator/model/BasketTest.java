package max.taxcalculator.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static max.taxcalculator.model.ItemType.CD;
import static max.taxcalculator.model.ItemType.MEDICAL;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class BasketTest {

    @Test
    public void it_adds_elements_to_the_basket_by_the_quantity(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        Basket basket = new Basket();
        basket.addItem(2,item);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(2,item))));
    }

    @Test
    public void it_adds_multiple_elements_to_the_basket_by_the_quantity(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        Item anotherItem = new Item().withDescription("Another Item").withType(MEDICAL).withPrice(new BigDecimal(10));
        Basket basket = new Basket();
        basket.addItem(2,item);
        basket.addItem(3,anotherItem);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(2,item),new BasketItem(3,anotherItem))));
    }

    @Test
    public void when_same_product_is_added_twice_the_quantity_is_updated(){
        Item item = new Item().withDescription("An Item").withType(CD).withPrice(new BigDecimal(10));
        Basket basket = new Basket();
        basket.addItem(2,item);
        basket.addItem(3,item);
        assertThat(basket.getItems(),is(Arrays.asList(new BasketItem(5,item))));
    }

}