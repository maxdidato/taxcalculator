package max.taxcalculator.strategy;

import max.taxcalculator.model.BasketItem;

import java.math.BigDecimal;

public interface Calculator {
    BigDecimal calculate(BasketItem basketItem);
}
