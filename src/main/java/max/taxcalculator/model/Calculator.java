package max.taxcalculator.model;

import java.math.BigDecimal;

public interface Calculator {
    BigDecimal calculate(BasketItem basketItem);
}
