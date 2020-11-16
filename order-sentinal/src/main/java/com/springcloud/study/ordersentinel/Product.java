package com.springcloud.study.ordersentinel;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;


    private String name;


    private BigDecimal price;


    private String stock;

}
