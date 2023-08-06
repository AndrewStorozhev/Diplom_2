package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Methods;
import praktikum.Steps;
import praktikum.user.LoginUser;
import praktikum.user.User;
import praktikum.user.UserGenerator;


public class GetOrderTest {
    private User user;
    private Steps steps;
    private Methods methods;
    private String accessToken;
    private LoginUser loginUser;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
        loginUser = new LoginUser(user);
    }

    @Test
    @DisplayName("Получение списка заказов без регистрации")
    public void getOrderWithoutUserTest() {
        ValidatableResponse response = steps.getWithoutUserOrders();
        methods.orderWithoutUser(response);
    }

    @Test
    @DisplayName("Получение списка заказов зарегестрированого пользователя")
    public void getOrderUserTest() {
        steps.create(user);
        ValidatableResponse loginResponse = steps.login(loginUser);
        accessToken = loginResponse.extract().path("accessToken").toString();

        String ingredients = "{\n\"ingredients\":[\"61c0c5a71d1f82001bdaaa6d\",\n" +
                "\"61c0c5a71d1f82001bdaaa6f\",\n" +
                "\"61c0c5a71d1f82001bdaaa72\"]\n}";

        steps.create(accessToken, ingredients);

        steps.getOrders(accessToken);
    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.delete(accessToken);
        }
    }
}
