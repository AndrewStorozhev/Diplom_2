package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;


public class UserGenerator {
    public static User generic() {
        return new User("test-data@yandex.ru", "1234", "Enot");

    }

    public static User random() {
        return new User(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", "1234", "Enot");
    }

}
