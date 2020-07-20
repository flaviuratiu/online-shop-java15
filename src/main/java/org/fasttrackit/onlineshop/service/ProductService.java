package org.fasttrackit.onlineshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistence.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshop.transfer.product.ProductResponse;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// Spring Bean (services, repositories etc)
@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    // IoC (Inversion of Control)
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    // Dependency Injection
    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public ProductResponse createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return mapProductResponse(savedProduct);
    }

    public ProductResponse getProductResponse(long id) {
        LOGGER.info("Retrieving product {}", id);

//        Optional<Product> productOptional = productRepository.findById(id);
////        if (productOptional.isPresent()) {
////            return productOptional.get();
////        } else {
////            throw new ResourceNotFoundException("Product " + id + " not found.");
////        }

        Product product = getProduct(id);

        return mapProductResponse(product);
    }

    public Product getProduct(long id) {
        return productRepository.findById(id)
                    // lambda expression
                    .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));
    }

    @Transactional
    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {
//        if (request.getPartialName() != null && request.getMinimumQuantity() != null) {
//
//            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(
//                    request.getPartialName(), request.getMinimumQuantity(), pageable);
//
//        } else if (request.getPartialName() != null) {
//
//            return productRepository.findByNameContaining(request.getPartialName(), pageable);
//        } else {
//            return productRepository.findAll(pageable);
//        }

        Page<Product> page = productRepository.findByOptionalCriteria(
                request.getPartialName(), request.getMinimumQuantity(), pageable);

        List<ProductResponse> productDtos = new ArrayList<>();

        for (Product product : page.getContent()) {
            ProductResponse productResponse = mapProductResponse(product);

            productDtos.add(productResponse);
        }

        return new PageImpl<>(productDtos, pageable, page.getTotalElements());
    }

    public ProductResponse updateProduct(long id, SaveProductRequest request ) {
        LOGGER.info("Updating product {}: {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        Product savedProduct = productRepository.save(product);

        return mapProductResponse(savedProduct);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
    }

    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());

        return productResponse;
    }
}
