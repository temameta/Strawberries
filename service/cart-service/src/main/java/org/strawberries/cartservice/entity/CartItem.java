package org.strawberries.cartservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartItem {
    UUID productId;
    int quantity;
    BigDecimal productPrice;
    int discount;
    BigDecimal priceWithDiscount;
}
