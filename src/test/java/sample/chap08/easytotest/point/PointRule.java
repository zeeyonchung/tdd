package sample.chap08.easytotest.point;

import java.time.LocalDate;

/**
 * 포인트를 계산한다.
 */
public class PointRule {

    public int calculate(Subscription subscription, Product product, LocalDate date) {
        int point = 0;
        if (subscription.isFinised(date)) {
            point += product.getDefaultPoint();
        }
        else {
            point += product.getDefaultPoint() + 10;
        }

        if (subscription.getGrade() == SubscriptionGrade.GOLD) {
            point += 100;
        }
        return point;
    }
}
