package sample.chap08.easytotest.pay;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaySyncTest {
    // 대역 생성
    private MemoryPayInfoDao memoryPayInfoDao = new MemoryPayInfoDao();

    @Test
    void syncTest() throws IOException {
        PaySync paySync = new PaySync(memoryPayInfoDao);
        paySync.sync("src/test/resources/chap08/c0111.csv");
        List<PayInfo> savedInfos = memoryPayInfoDao.getAll();
        assertEquals(2, savedInfos.size());
    }
}