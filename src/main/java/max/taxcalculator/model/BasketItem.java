package max.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class BasketItem {

    private double quantity;
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItem that = (BasketItem) o;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + item.hashCode();
        return result;
    }
}
