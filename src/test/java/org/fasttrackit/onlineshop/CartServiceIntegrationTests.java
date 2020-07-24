package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.User;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.ProductTestSteps;
import org.fasttrackit.onlineshop.steps.UserTestSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.onlineshop.transfer.cart.CartResponse;
import org.fasttrackit.onlineshop.transfer.product.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void addProductsToCart_whenNewUser_thenCreateCartForUser() {
        User user = userTestSteps.createUser();

        ProductResponse product = productTestSteps.createProduct();

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        request.setProductIds(Collections.singletonList(product.getId()));

        cartService.addProductsToCart(user.getId(), request);

        CartResponse cartResponse = cartService.getCart(user.getId());

        assertThat(cartResponse, notNullValue());
        assertThat(cartResponse.getId(), is(user.getId()));
        assertThat(cartResponse.getProducts(), notNullValue());
        assertThat(cartResponse.getProducts(), hasSize(1));
        assertThat(cartResponse.getProducts().get(0), notNullValue());
        assertThat(cartResponse.getProducts().get(0).getId(), is(product.getId()));
        assertThat(cartResponse.getProducts().get(0).getName(), is(product.getName()));
        assertThat(cartResponse.getProducts().get(0).getPrice(), is(product.getPrice()));
        assertThat(cartResponse.getProducts().get(0).getImageUrl(), is(product.getImageUrl()));
    }
}
