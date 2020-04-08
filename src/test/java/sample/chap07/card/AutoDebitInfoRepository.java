package sample.chap07.card;

public interface AutoDebitInfoRepository {
    AutoDebitInfo findOne(String userId);
    void save(AutoDebitInfo newInfo);
}
