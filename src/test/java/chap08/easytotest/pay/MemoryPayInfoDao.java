package chap08.easytotest.pay;

import java.util.ArrayList;
import java.util.List;

public class MemoryPayInfoDao implements PayInfoDao {

    private List<PayInfo> payInfos = new ArrayList<>();

    @Override
    public void insert(PayInfo payInfo) {
        payInfos.add(payInfo);
    }

    public List<PayInfo> getAll() {
        return payInfos;
    }
}
