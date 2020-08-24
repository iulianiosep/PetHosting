package com.petHosting.repository;

import com.petHosting.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, String> {
    @Override
    void delete(Products deleted);
}
