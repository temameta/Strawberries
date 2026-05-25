package org.strawberries.cartservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RedisHash(value = "carts", timeToLive = 604800L)
@Getter
@Setter
@Builder
public class UserCart {
    @Id
    UUID userId;
    List<CartItem> items;
    BigDecimal totalPrice;
    @CreatedDate
    OffsetDateTime createdAt;
    @LastModifiedDate
    OffsetDateTime updatedAt;
}
