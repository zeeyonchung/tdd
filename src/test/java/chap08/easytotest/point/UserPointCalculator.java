package chap08.easytotest.point;

import java.time.LocalDate;

/**
 * 테스트하기 쉬운 코드
 * - 의존 대상을 주입받음
 * - 역할에 따라 분리된 코드
 *
 * 유저의 구독 상태나 제품에 따라 유저의 포인트를 계산한다.
 */
public class UserPointCalculator {
    private SubsriptionDao subsriptionDao;
    private ProductDao productDao;
    private PointRule pointRule;
    private Times times = new Times();

    public UserPointCalculator(SubsriptionDao subsriptionDao, ProductDao productDao, PointRule pointRule) {
        this.subsriptionDao = subsriptionDao;
        this.productDao = productDao;
        this.pointRule = pointRule;
    }

    public int calculatePoint(User u) {
        Subscription s = subsriptionDao.selectByUser(u.getId());

        if (s == null) {
            throw new NoSubscriptionException();
        }

        // 오늘 일자를 구하는 기능을 Times로 분리하였다.
        // given(times.today()).willReturn(LocalDate.of(2020, 4, 5)); 이제 이렇게 테스트 코드를 짤 수 있다.
        LocalDate now = times.today();

        Product p = productDao.selectById(s.getProductId());

        // 포인트 계산 기능을 PointRule로 분리하였다.
        return pointRule.calculate(s, p, now);
    }
}
