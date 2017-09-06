package max.taxcalculator.utilities;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class CurrencyUtils {
    public static BigDecimal bigDecimalScaleTwo(double amount) {
        return new BigDecimal(amount).setScale(2,ROUND_HALF_EVEN);
    }
}
