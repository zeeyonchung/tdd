package sample.chap08.easytotest.point;

import java.time.LocalDate;

/**
 * 현재 일자를 구한다.
 */
public class Times {

    public LocalDate today() {
        return LocalDate.now();
    }
}
