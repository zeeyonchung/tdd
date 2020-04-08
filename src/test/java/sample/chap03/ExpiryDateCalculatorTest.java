package sample.chap03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    @Test
    @DisplayName("만원을 납부하면 한 달 뒤 만료된다")
    void pay10000Won_Then_expire1MonthLater() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 3, 30))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 4, 30));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 5, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 6, 1));
    }

    @Test
    @DisplayName("납부일과 한달 뒤의 일자가 일치하지 않음")
    void billingDateNotEqualExpiryDate_Then_expire1MonthLater() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 2, 29));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 5, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 6, 30));
    }

    @Test
    @DisplayName("첫 납부일과 한달 뒤의 일자가 일치하지 않을 때 만원을 납부하면 첫 납부일 기준으로 다음 만료일을 정한다")
    void billingDateNotEqualExpiryDate_repay_Then_expireByFirstPayDate() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2020, 1, 31))
                .billingDate(LocalDate.of(2020, 2, 29))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2020, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2020, 1, 30))
                .billingDate(LocalDate.of(2020, 2, 29))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2020, 3, 30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2020, 5, 31))
                .billingDate(LocalDate.of(2020, 6, 30))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2020, 7, 31));
    }

    @Test
    @DisplayName("2만원 이상 지불하면 비례해서 만료된다")
    void pay20000Won_Then_Expire2MonthsLater() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 3, 1))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2020, 5, 1));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 3, 31))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2020, 6, 30));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 3, 31))
                        .payAmount(80_000)
                        .build(),
                LocalDate.of(2020, 11, 30));
    }

    @Test
    @DisplayName("첫 납부일과 한달 뒤의 일자가 일치하지 않을 때 2만원 이상을 납부하면 첫 납부일 기준으로 다음 만료일을 정한다")
    void billingDateNotEqualExpiryDate_repay20000Won_Then_expireByFirstPayDate() {
        assertExpiryDate(PayData.builder()
                        .firstBillingDate(LocalDate.of(2020, 1, 31))
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2020, 4, 30));

        assertExpiryDate(PayData.builder()
                        .firstBillingDate(LocalDate.of(2020, 3, 31))
                        .billingDate(LocalDate.of(2020, 4, 30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2020, 7, 31));
    }

    @Test
    @DisplayName("10만원을 납부하면 1년 뒤 만료된다")
    void pay100000Won_Then_Expire1YearLater() {
        assertExpiryDate(PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021, 2, 28));
    }

    @Test
    void 십만원이상_납부하면_십만원에비례한년수와_나머지달만큼_제공() {
        assertExpiryDate(PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2021, 5, 29));

        assertExpiryDate(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(170_000)
                        .build(),
                LocalDate.of(2020, 8, 31));

        assertExpiryDate(PayData.builder()
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(200_000)
                        .build(),
                LocalDate.of(2021, 2, 28));

        assertExpiryDate(PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(230_000)
                        .build(),
                LocalDate.of(2022, 5, 29));
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator calculator = new ExpiryDateCalculator();
        LocalDate realExpiryDate = calculator.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }
}
