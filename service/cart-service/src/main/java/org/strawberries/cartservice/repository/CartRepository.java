package org.strawberries.cartservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.strawberries.cartservice.entity.UserCart;

import java.util.UUID;

public interface CartRepository extends CrudRepository<UserCart, UUID> {
}
