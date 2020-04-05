package chap08.easytotest.pay;

public class PayInfo {
    private final String dateTime;
    private final String trNum;
    private final int amounts;

    public PayInfo(String dateTime, String trNum, int amounts) {
        this.dateTime = dateTime;
        this.trNum = trNum;
        this.amounts = amounts;
    }
}
