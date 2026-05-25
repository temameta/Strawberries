package org.strawberries.orderservice.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.strawberries.orderapi.codegen.types.Order;
import org.strawberries.orderevents.EventEnvelope;
import org.strawberries.orderevents.OrderEvent;
import org.strawberries.orderevents.RoutingKeys;
import org.strawberries.orderservice.entity.OrderEntity;
import org.strawberries.orderservice.mapper.ItemMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String SOURCE = this.getClass().getPackageName();
    private final ItemMapper itemMapper;

    public void publishCreated(OrderEntity order) {
        send(RoutingKeys.ORDER_CREATED, new OrderEvent.Created(
                order.getId(),
                order.getUserId(),
                order.getAddress(),
                order.getItems().stream()
                        .map(itemMapper::toEventItem)
                        .toList()
        ));
    }

    public void publishCancelled(OrderEntity order) {
        send(RoutingKeys.ORDER_CANCELLED, new OrderEvent.Cancelled(
                order.getId(),
                order.getUserId(),
                order.getItems().stream()
                        .map(itemMapper::toEventItem)
                        .toList()));
    }

    private void send(String routingKey, OrderEvent event) {
        try {
            EventEnvelope<OrderEvent> envelope = EventEnvelope.wrap(event, SOURCE, routingKey);
            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, routingKey, envelope);
            log.info("Событие отправлено: {} [eventId={}]", routingKey, envelope.metadata().eventId());
        } catch (Exception e) {
            log.error("Не удалось отправить событие {}: {}", routingKey, e.getStackTrace());
        }
    }
}
