package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.User;
import org.fasttrackit.onlineshop.domain.UserRole;
import org.fasttrackit.onlineshop.service.UserService;
import org.fasttrackit.onlineshop.transfer.user.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Test
    public void createUser_whenValidRequest_thenReturnCreatedUser() {
        createUser();
    }

    @Test
    public void getUser_whenExistingUser_thenReturnUser() {
        User user = createUser();

        User userResponse = userService.getUser(user.getId());

        assertThat(userResponse, notNullValue());
        assertThat(userResponse.getId(), is(user.getId()));
        assertThat(userResponse.getRole(), is(user.getRole()));
        assertThat(userResponse.getFirstName(), is(user.getFirstName()));
        assertThat(userResponse.getLastName(), is(user.getLastName()));
    }

    private User createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setRole(UserRole.CUSTOMER);
        request.setFirstName("Test First Name");
        request.setLastName("Test Last Name");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getRole(), is(request.getRole().name()));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getLastName(), is(request.getLastName()));

        return user;
    }
}
