package chap07.card.stub;

import chap07.card.AutoDebitInfo;
import chap07.card.AutoDebitInfoRepository;

/**
 * AutoDebitInfoRepository의 대역
 * 구현이 없는 빈 깡통.
 */
public class StubAutoDebitInfoRepository implements AutoDebitInfoRepository {
    @Override
    public AutoDebitInfo findOne(String userId) {
        return null;
    }

    @Override
    public void save(AutoDebitInfo newInfo) {

    }
}
