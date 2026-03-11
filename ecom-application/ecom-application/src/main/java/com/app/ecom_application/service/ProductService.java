package com.app.ecom_application.service;

import com.app.ecom_application.dto.ProductRequest;
import com.app.ecom_application.dto.ProductResponse;
import com.app.ecom_application.model.Product;
import com.app.ecom_application.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct){
        ProductResponse response = new ProductResponse();
        response.setProductId(savedProduct.getProductId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setCategory(savedProduct.getCategory());
        response.setActive(savedProduct.isActive());
        return response;
    }
    private void updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(productRequest.getCategory());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest updatedProductRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct,updatedProductRequest);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(updatedProduct);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToProductResponse)
                        .toList();
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product ->{
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);

    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
