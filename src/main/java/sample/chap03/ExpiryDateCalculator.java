package sample.chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {
        int yearsToAdd = payData.getPayAmount() / 100_000;
        int monthsToAdd = (payData.getPayAmount() % 100_000) / 10_000;

        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, yearsToAdd, monthsToAdd);

        } else {
            return payData.getBillingDate().plusMonths(monthsToAdd).plusYears(yearsToAdd);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int yearsToAdd, int monthsToAdd) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(monthsToAdd).plusYears(yearsToAdd);
        int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();

        if (isSameDayOfMonth(candidateExp, dayOfFirstBilling)) {
            int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
            if (dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        }

        return candidateExp;
    }

    private int lastDayOfMonth(LocalDate date) {
        return YearMonth.from(date).lengthOfMonth();
    }

    private boolean isSameDayOfMonth(LocalDate date1, int date2) {
        return date2 != date1.getDayOfMonth();
    }
}
