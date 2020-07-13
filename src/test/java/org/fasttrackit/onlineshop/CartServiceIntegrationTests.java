package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.User;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.UserTestSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void addProductsToCart_whenNewUser_thenCreateCartForUser() {
        User user = userTestSteps.createUser();

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        // add product ids

        cartService.addProductsToCart(user.getId(), request);
    }
}
