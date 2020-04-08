package sample.chap08.hardtotest.point;

import java.time.LocalDateTime;

/**
 * 테스트하기 어려운 코드
 * - 섞여있는 역할
 * - 실행 시점에 따라 결과가 달라지는 코드
 *
 * 유저의 구독 상태나 제품에 따라 유저의 포인트를 계산한다.
 */
public class UserPointCalculator {
    //역할이 섞여있다. 포인트 계산에서 실제 필요한 건 dao가 아니라 Subscription과 Product다.
    private SubsriptionDao subsriptionDao;
    private ProductDao productDao;

    public UserPointCalculator(SubsriptionDao subsriptionDao, ProductDao productDao) {
        this.subsriptionDao = subsriptionDao;
        this.productDao = productDao;
    }

    public int calculatePoint(User u) {
        Subscription s = subsriptionDao.selectByUser(u.getId());

        if (s == null) {
            throw new NoSubscriptionException();
        }

        // 실행시점에 따라 결과가 달라진다.
        LocalDateTime now = LocalDateTime.now();
        Product p = productDao.selectById(s.getProductId());
        int point = 0;

        if (s.isFinised(now)) {
            point += p.getDefaultPoint();
        }
        else {
            point += p.getDefaultPoint() + 10;
        }

        if (s.getGrade() == SubscriptionGrade.GOLD) {
            point += 100;
        }

        return point;
    }
}
