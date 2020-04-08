package sample.chap08.hardtotest.point;

import java.time.LocalDateTime;

public class Subscription {
    public String getProductId() {
        return null;
    }

    public boolean isFinised(LocalDateTime dateTime) {
        return false;
    }

    public SubscriptionGrade getGrade() {
        return SubscriptionGrade.GOLD;
    }
}
