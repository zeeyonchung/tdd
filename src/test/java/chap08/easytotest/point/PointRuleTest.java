package chap08.easytotest.point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PointRuleTest {

    @Test
    void 만료전_골드등급은_130포인트() {
        PointRule rule = new PointRule();
        Subscription subscription = new Subscription(LocalDate.of(2020, 4, 5), SubscriptionGrade.GOLD);
        Product product = new Product();
        product.setDefaultPoint(20);

        int point = rule.calculate(subscription, product, LocalDate.of(2020, 4, 1));

        Assertions.assertEquals(130, point);
    }
}