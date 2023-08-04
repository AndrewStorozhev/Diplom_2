package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Methods {
    @Step("Проверка кода ответа")
    public void createUserResponse(ValidatableResponse response, int code, Boolean status) {
        response.assertThat().statusCode(200).body("success", is(true));
    }

    @Step("Проверка кода ответа при создании пользователя без email, password and name")
    public void correctCreateUserResponse(ValidatableResponse response) {
        response.statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка кода ответа при регистрации существующего пользователя")
    public void correctUserDoubleResponse(ValidatableResponse response) {
        response.statusCode(403).and().assertThat().body("message", equalTo("User already exists"));
    }

    @Step("Проверка кода ответа при неправильном email или password")
    public void emailOrPasswordIncorrect(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка кода ответа при получении заказа без пользователя")
    public void updateWithoutUser(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));

    }

    @Step("Проверка кода ответа при создании заказа без пользователя")
    public void orderWithoutUser(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка кода ответа при создании заказа с неверным хешем ингредиентов")
    public void assertErrorMessage(ValidatableResponse response) {
        response.statusCode(500);
    }

    @Step("Проверка кода ответа при создании заказа без ингредиентов")
    public void assertWithoutIngredientsError(ValidatableResponse response) {
        response.statusCode(400).and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка кода ответа при успешном создании заказа")
    public void assertOrderCreated(ValidatableResponse response) {
        response.statusCode(400);
    }

    @Step("Проверка кода ответа создания заказа")
    public void createOrderResponse(ValidatableResponse response, int expectedStatusCode, boolean expectedSuccess) {
        response.assertThat()
                .statusCode(expectedStatusCode)
                .body("success", is(expectedSuccess));
    }

}
