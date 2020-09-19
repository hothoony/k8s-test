package mystudy.k8stest;

import org.junit.jupiter.api.Test;

public class Test2 {

    @Test
    void test() {
        System.out.println("test2");
        throw new RuntimeException();
    }
}
