package chap07.card.fake;

import chap07.card.AutoDebitInfo;
import chap07.card.AutoDebitInfoRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * AutoDebitInfoRepository의 대역
 * 실제로 쓸 수는 없지만 실제 동작하는 기능을 제공한다.
 */
public class MemoryAutoDebitInfoRepository implements AutoDebitInfoRepository {
    private Map<String, AutoDebitInfo> infos = new HashMap<>();

    @Override
    public AutoDebitInfo findOne(String userId) {
        return infos.get(userId);
    }

    @Override
    public void save(AutoDebitInfo newInfo) {
        infos.put(newInfo.getUserId(), newInfo);
    }
}
