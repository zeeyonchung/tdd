package sample.chap02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String s, PasswordStrength strong) {
        PasswordStrength result = meter.meter(s);
        assertEquals(strong, result);
    }

    @Test
    @DisplayName("암호가 모든 조건을 충족하면 암호 강도는 강함이어야 한다")
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    @Test
    @DisplayName("길이가 8글자 미만이고 나머지 조건을 충족하면 암호 강도는 보통이어야 한다")
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("숫자를 포함하지 않고 나머지 조건을 충족하면 암호 강도는 보통이어야 한다")
    void meetsOtherCriteria_except_for_number_Then_NorMal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("값이 null인 경우 암호 강도는 유효하지 않음이어야 한다")
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    @DisplayName("값이 빈 문자열인 경우 암호 강도는 유효하지 않음이어야 한다")
    void emptyInput_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    @DisplayName("대문자를 포함하지 않고 나머지 조건을 충족하면 암호 강도는 보통이어야 한다")
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("길이가 8글자 이상인 조건만 만족하는 경우 암호 강도는 약함이어야 한다")
    void meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("qwerasdf", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("숫자를 포함하는 조건만 만족하는 경우 암호 강도는 약함이어야 한다")
    void meetsOnlyNumCriteria_Then_Weak() {
        assertStrength("12345", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("대문자를 포함하는 조건만 만족하는 경우 암호 강도는 약함이어야 한다")
    void meetsOnlyUpperCriteria_Then_Weak() {
        assertStrength("ABCDE", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("아무 조건도 충족하지 않는 경우 암호 강도는 약함이어야 한다")
    void meetsNoCriteria_Then_Weak() {
        assertStrength("qwer", PasswordStrength.WEAK);
    }
}
