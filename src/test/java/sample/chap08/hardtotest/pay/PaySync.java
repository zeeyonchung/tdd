package sample.chap08.hardtotest.pay;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 테스트하기 어려운 코드
 * - 의존 대상을 직접 생성
 * - 경로 하드코딩
 *
 * 결제 결과 파일을 읽어와 DB에 저장한다.
 */
public class PaySync {
    // 의존 대상을 직접 생성
    private PayInfoDao payInfoDao = new PayInfoDao();

    private void sync() throws IOException {
        // 경로 하드코딩
        Path path = Paths.get("D:\\data\\pay\\cp0001.csv");
        List<PayInfo> payInfos = Files.lines(path)
                .map(line -> {
                    String[] data = line.split(",");
                    return new PayInfo(data[0], data[1], Integer.parseInt(data[2]));
                })
                .collect(Collectors.toList());

        payInfos.forEach(pi -> payInfoDao.insert(pi));
    }
}
