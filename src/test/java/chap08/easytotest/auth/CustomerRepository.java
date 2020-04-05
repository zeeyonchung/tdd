package chap08.easytotest.auth;

public interface CustomerRepository {
    Customer findOne(String id);
}
