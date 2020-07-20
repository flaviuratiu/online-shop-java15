package org.fasttrackit.onlineshop.transfer.cart;

import java.util.List;

public class CartResponse {

    private long id;
    private List<ProductInCartResponse> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductInCartResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInCartResponse> products) {
        this.products = products;
    }
}
