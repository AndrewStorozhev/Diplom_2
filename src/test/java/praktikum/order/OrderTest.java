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

public class OrderTest {
    private Steps steps;
    private User user;
    private Methods methods;
    private LoginUser loginUser;

    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
        loginUser = new LoginUser(user);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutUserTest() {
        String ingredients = "";
        ValidatableResponse response = steps.create(ingredients);
        methods.createOrderResponse(response, 400, false);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderIncorrectIngredientTest() {
        steps.create(user);
        ValidatableResponse response = steps.login(loginUser);
        accessToken = response.extract().path("accessToken").toString();

        String[] invalidIngredients = {"1", "2", "3"};
        ValidatableResponse createOrderResponse = steps.createInvalidIngredients(accessToken, invalidIngredients);
        methods.assertErrorMessage(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderTest() {
        steps.create(user);
        ValidatableResponse loginResponse = steps.login(loginUser);
        accessToken = loginResponse.extract().path("accessToken").toString();

        String ingredients = "{\n\"ingredients\":[\"61c0c5a71d1f82001bdaaa6d\",\n" +
                "\"61c0c5a71d1f82001bdaaa6f\",\n" +
                "\"61c0c5a71d1f82001bdaaa72\"]\n}";

        ValidatableResponse createOrderResponse = steps.create(accessToken, ingredients);
        steps.getOrders(accessToken);
        methods.assertOrderCreated(createOrderResponse);

    }

    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    public void createOrderWithoutIngredientTest() {
        steps.create(user);
        ValidatableResponse response = steps.login(loginUser);
        accessToken = response.extract().path("accessToken").toString();
        ValidatableResponse createOrderResponse = steps.createWithoutIngredients(accessToken);
        methods.assertWithoutIngredientsError(createOrderResponse);
    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.delete(accessToken);
        }
    }
}
