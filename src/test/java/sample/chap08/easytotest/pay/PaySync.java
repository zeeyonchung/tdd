package sample.chap08.easytotest.pay;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 테스트하기 쉬운 코드
 * - 의존 대상을 주입받음
 * - 하드코딩된 상수를 주입받음
 *
 * 결제 결과 파일을 읽어와 DB에 저장한다.
 */
public class PaySync {
    private PayInfoDao payInfoDao;

    public PaySync(PayInfoDao payInfoDao) {
        this.payInfoDao = payInfoDao;
    }

    public void sync(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<PayInfo> payInfos = Files.lines(path)
                .map(line -> {
                    String[] data = line.split(",");
                    return new PayInfo(data[0], data[1], Integer.parseInt(data[2]));
                })
                .collect(Collectors.toList());

        payInfos.forEach(pi -> payInfoDao.insert(pi));
    }
}
