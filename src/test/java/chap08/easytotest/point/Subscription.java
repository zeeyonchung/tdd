package chap08.easytotest.point;

import java.time.LocalDate;

public class Subscription {
    private String productId;
    private LocalDate expireDate;
    private SubscriptionGrade grade;

    public Subscription(LocalDate expireDate, SubscriptionGrade grade) {
        this.expireDate = expireDate;
        this.grade = grade;
    }

    public String getProductId() {
        return productId;
    }

    public SubscriptionGrade getGrade() {
        return grade;
    }

    public boolean isFinised(LocalDate date) {
        return expireDate.isBefore(date);
    }
}
