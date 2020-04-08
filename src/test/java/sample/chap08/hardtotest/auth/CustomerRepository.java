package sample.chap08.hardtotest.auth;

public interface CustomerRepository {
    Customer findOne(String id);
}
