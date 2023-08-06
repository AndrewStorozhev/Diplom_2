package praktikum;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.user.LoginUser;
import praktikum.user.User;

import java.util.Arrays;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Steps extends Config {

    private String createRequestBody(String[] ingredients) {
        return "{\n\"ingredients\": [" + Arrays.toString(ingredients) + "]\n}";
    }

    @Step("Создание нового пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .log().all();
    }

    @Step("Авторизация зарегестрированного пользователя")
    public ValidatableResponse login(LoginUser loginUser) {
        return given()
                .log().all()
                .spec(getSpec())
                .body(loginUser)
                .when()
                .post(LOGIN)
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then()
                .log().all();
    }

    @Step("Редактирование пользователя")
    public ValidatableResponse update(String accessToken, User user) {
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then()
                .log().all();
    }

    @Step("Получение данных пользователя")
    public ValidatableResponse get(String accessToken) {
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(USER)
                .then()
                .log().all();
    }

    @Step("Создание заказа неавторизированного пользователя")
    public ValidatableResponse create(String ingredients) {
        //String requestBody = this.createRequestBody(ingredients);
        return given()
                .spec(getSpec())
                .when()
                .body(ingredients)
                .post(ORDER)
                .then()
                .log().all();
    }

    @Step("Создание заказа авторизированного пользователя")
    public ValidatableResponse create(String accessToken, String ingredients) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(ingredients)
                .post(ORDER)
                .then()
                .log().all();
    }

    @Step("Создание заказа авторизированного пользователя с неверным хешем ингредиентов")
    public ValidatableResponse createInvalidIngredients(String accessToken, String[] ingredients) {
        String requestBody = this.createRequestBody(ingredients);
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(requestBody)
                .post(ORDER)
                .then()
                .log().all()
                .statusCode(500);

    }

    @Step("Создание заказа авторизированного пользователя без ингридиентов")
    public ValidatableResponse createWithoutIngredients(String accessToken) {
        String requestBody = "";
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(requestBody)
                .post(ORDER)
                .then()
                .log().all()
                .statusCode(400)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Получение заказа")
    public ValidatableResponse getOrders(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение заказа без пользователя")
    public ValidatableResponse getWithoutUserOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER)
                .then()
                .log().all();
    }
}
