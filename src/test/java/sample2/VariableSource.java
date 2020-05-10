package sample2;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(VariableArgumentProvider.class)
public @interface VariableSource {

    /**
     * static 변수의 이름
     */
    String value();
}
