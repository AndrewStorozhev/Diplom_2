package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Methods;
import praktikum.Steps;


public class UserTest {
    private User user;
    private Steps steps;
    private Methods methods;
    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
    }

    @Test
    @DisplayName("Тестирование создание нового пользователя")
    public void creatureUserTest() {
        ValidatableResponse response = steps.create(user);
        accessToken = response.extract().path("accessToken").toString();
        methods.createUserResponse(response, code, status);

    }

    @Test
    @DisplayName("Тестирование создание пользователя без name")
    public void creatureUserWithoutNameTest() {
        user.setName("");
        ValidatableResponse response = steps.create(user);
        methods.correctCreateUserResponse(response);

    }

    @Test
    @DisplayName("Тестирование создание пользователя без email")
    public void creatureUserWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = steps.create(user);
        methods.correctCreateUserResponse(response);

    }

    @Test
    @DisplayName("Тестирование создание пользователя без password")
    public void creatureUserWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = steps.create(user);
        methods.correctCreateUserResponse(response);
    }

    @Test
    @DisplayName("Тестирование регистрации существующего пользователя")
    public void creatureUserDoubleTest() {
        ValidatableResponse responseFirst = steps.create(user);
        methods.createUserResponse(responseFirst, code, status);

        ValidatableResponse responseSecond = steps.create(user);

        methods.correctUserDoubleResponse(responseSecond);

        String firstUserAccessToken = responseFirst.extract().path("accessToken").toString();
        steps.delete(firstUserAccessToken);
    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.delete(accessToken);
        }
    }
}
