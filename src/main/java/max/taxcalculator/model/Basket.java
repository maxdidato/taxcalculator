package max.taxcalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    List<BasketItem> items;

    public Basket() {
        items = new ArrayList<>();
    }

    public void addItem(int quantity, Item item) {
        BasketItem basketItem = new BasketItem(quantity, item);
        int i = this.items.indexOf(basketItem);
        if (i>=0){
            basketItem = items.get(i);
            basketItem.setQuantity(quantity+basketItem.getQuantity());
        }else {
            this.items.add(basketItem);
        }
    }

    public List<BasketItem> getItems(){
        return items;
    }
}
