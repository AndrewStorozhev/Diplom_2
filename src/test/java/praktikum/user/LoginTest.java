package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Methods;
import praktikum.Steps;


public class LoginTest {
    private User user;
    private Steps steps;
    private LoginUser loginUser;
    private Methods methods;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
        loginUser = new LoginUser(user);
    }

    @Test
    @DisplayName("Тестирование авторизации пользователя")
    public void authorizationTest() {
        steps.create(user);
        ValidatableResponse response = steps.login(loginUser);
        accessToken = response.extract().path("accessToken").toString();
        methods.createUserResponse(response);
    }

    @Test
    @DisplayName("Авторизация пользователя без email")
    public void authorizationWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = steps.login(loginUser);
        methods.emailOrPasswordIncorrect(response);

    }

    @Test
    @DisplayName("Авторизация пользователя без password")
    public void authorizationWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = steps.login(loginUser);
        methods.emailOrPasswordIncorrect(response);

    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.delete(accessToken);
        }
    }
}
