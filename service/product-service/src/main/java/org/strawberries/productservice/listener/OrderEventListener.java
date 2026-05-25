package org.strawberries.productservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.strawberries.orderevents.EventMetadata;
import org.strawberries.orderevents.Item;
import org.strawberries.orderevents.OrderEvent;
import org.strawberries.productservice.service.ProductService;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {
    private final ProductService service;
    private final JsonMapper jsonMapper;

    @RabbitListener(queues = "q.orders.events", messageConverter = "")
    public void handleEvent(Message message) {
        try {
            byte[] body = message.getBody();
            JsonNode root = jsonMapper.readTree(body);

            // Извлекаем метаданные из JSON-конверта
            JsonNode metaNode = root.get("metadata");
            EventMetadata metadata = jsonMapper.treeToValue(metaNode, EventMetadata.class);
            log.info("Получено сообщение {}", metadata.eventType());

            // Определяем тип события и формируем описание
            JsonNode payloadNode = root.get("payload");

            switch (metadata.eventType()) {
                case "order.created" -> {
                    OrderEvent.Created event = jsonMapper.treeToValue(payloadNode, OrderEvent.Created.class);
                    for (Item item : event.items()) {
                        service.decreaseQuantity(item.getProductId(), item.getProductAmount());
                    }
                }
                case "order.cancelled" -> {
                    OrderEvent.Cancelled event = jsonMapper.treeToValue(payloadNode, OrderEvent.Cancelled.class);
                    for (Item item : event.items()) {
                        service.increaseQuantity(item.getProductId(), item.getProductAmount());
                    }
                }
                default -> log.error("Unknown event {}", metadata.eventType());
            }
        } catch (Exception e) {
            log.error("Ошибка обработки события: {}", e.getMessage(), e);
            // Исключение пробросится, сообщение уйдёт в DLQ после исчерпания retries
            throw new RuntimeException("Не удалось обработать событие", e);
        }
    }
}

