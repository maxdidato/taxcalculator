package unit;

import max.taxcalculator.utilities.CurrencyUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;
import static max.taxcalculator.utilities.CurrencyUtils.bigDecimalScaleTwo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class CurrencyUtilsTest {

    @Test
    public void it_generates_a_big_decimal_with_two_digits_rounded_to_half_even(){
        assertThat(bigDecimalScaleTwo(10.22790),is(new BigDecimal(10.23).setScale(2, HALF_EVEN)));
        assertThat(bigDecimalScaleTwo(10.22290),is(new BigDecimal(10.22).setScale(2, HALF_EVEN)));
    }


}