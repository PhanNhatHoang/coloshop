package com.web.coloshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_ID")
    private Long id;

    private String name;
    private String description;
    private Float costprice;
    private Float saleprice;
    private Integer currentQuantity;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String Image;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_ID")
    private Brand brand;

    private boolean is_deleted;
    private boolean is_activated;

}
