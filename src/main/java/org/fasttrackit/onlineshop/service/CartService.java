package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Cart;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.domain.User;
import org.fasttrackit.onlineshop.persistence.CartRepository;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public void addProductsToCart(long cartId, AddProductsToCartRequest request) {
        LOGGER.info("Adding products to cart {}: {}", cartId, request);

        Cart cart = cartRepository.findById(cartId)
                .orElse(new Cart());

        if (cart.getUser() == null) {
            User user = userService.getUser(cartId);

            cart.setUser(user);
        }

        for (Long productId : request.getProductIds()) {
            Product product = productService.getProduct(productId);
            cart.addProduct(product);
        }

        cartRepository.save(cart);
    }
}
