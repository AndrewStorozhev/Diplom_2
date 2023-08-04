package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Methods;
import praktikum.Steps;
import praktikum.user.LoginUser;
import praktikum.user.User;
import praktikum.user.UserGenerator;

import static org.hamcrest.Matchers.equalTo;

public class UpdateTest {
    private User user;
    private User userForUpdate;
    private Steps steps;
    private LoginUser loginUser;
    private Methods methods;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        userForUpdate = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
        loginUser = new LoginUser(user);
    }

    @Test
    @DisplayName("Изменения данных зарегестрированого пользователя")
    public void updateUserTest() {
        User initialUser = UserGenerator.random();
        User userForUpdate = initialUser.clone();
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(10) + "@pochta.com");

        String accessToken = steps.create(initialUser).extract().header("Authorization");
        steps.update(accessToken, userForUpdate);
        ValidatableResponse updatedUserResponse = steps.get(accessToken);

        updatedUserResponse.body("user.name", equalTo(initialUser.getName())).and().body("user.email", equalTo(userForUpdate.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Изменения данных не зарегестрированого пользователя")
    public void updateWithoutUserTest() {
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(10) + "@pochta.com");
        ValidatableResponse response = steps.update("", userForUpdate);
        methods.updateWithoutUser(response);

    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.delete(accessToken);
        }
    }

}
