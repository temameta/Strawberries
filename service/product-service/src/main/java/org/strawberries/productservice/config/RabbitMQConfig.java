package org.strawberries.productservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.strawberries.orderevents.RoutingKeys;
import tools.jackson.databind.json.JsonMapper;

/**
 * Конфигурация RabbitMQ на стороне потребителя (consumer).
 *
 * Здесь определяем:
 * - формат сообщений (JSON через Jackson),
 * - exchange (точка обмена) и очереди,
 * - привязки (bindings) между exchange и очередями,
 * - Dead Letter Queue (DLQ) для необработанных сообщений.
 */
@Configuration
public class RabbitMQConfig {

    // Имена очередей — каждый consumer обычно заводит свои очереди, не общие с другими сервисами.
    // Имена начинаются с «q.» — принятое соглашение для RabbitMQ.
    public static final String ORDER_QUEUE = "q.orders.events";
    public static final String ORDER_DLQ = "q.orders.events.dlq";

     /**
     * Конвертер сообщений — превращает Java-объекты в JSON и обратно.      
     * Принимаем ObjectMapper из контекста Spring Boot — он уже настроен
     * с поддержкой java.time (Instant, LocalDate) через автоконфигурацию.
     */
    @Bean
    public MessageConverter jsonMessageConverter(JsonMapper jsonMapper) {
        return new JacksonJsonMessageConverter(jsonMapper);
    }

    /**
     * RabbitTemplate используется в тестах и мониторинге.
     * Главное — подключить тот же Jackson-конвертер.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    /**
     * Фабрика контейнеров для @RabbitListener — настраиваем JSON-конвертер
     * и параллелизм (1 поток для учебной среды, в продакшене — больше).
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(3);
        // При ошибке десериализации сообщение попадёт в DLQ (настроено через x-dead-letter-exchange)
        factory.setDefaultRequeueRejected(false);
        return factory;
    }   

    /**
     * Topic exchange — точка обмена, через которую проходят все доменные события.
     *
     * Topic exchange маршрутизирует сообщения по routing key:
     * - "book.created"  → попадёт в очередь с binding key "book.*"
     * - "author.deleted" → попадёт в очередь с binding key "#" (все события)
     *
     * durable=true: exchange выживает перезапуск RabbitMQ.
     */
    @Bean
    public TopicExchange eventsExchange() {
        return ExchangeBuilder
                .topicExchange(RoutingKeys.EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * Dead Letter Exchange — отдельный exchange для «мёртвых» сообщений.
     *
     * Сообщение попадает сюда, если:
     * - consumer выбросил исключение (и requeue=false),
     * - TTL сообщения истёк,
     * - очередь переполнена.
     *
     * Direct exchange: маршрутизация по точному совпадению routing key.
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return ExchangeBuilder
                .directExchange(RoutingKeys.EXCHANGE + ".dlx")
                .durable(true)
                .build();
    }

    /**
     * Основная очередь аудита — слушает все доменные события (binding key "#").
     *
     * При ошибке обработки сообщение перенаправляется в DLQ через:
     * - x-dead-letter-exchange — куда отправить,
     * - x-dead-letter-routing-key — с каким routing key.
     */
    @Bean
    public Queue productQueue() {
        return QueueBuilder
                .durable(ORDER_QUEUE)
                .deadLetterExchange(RoutingKeys.EXCHANGE + ".dlx")
                .deadLetterRoutingKey(ORDER_DLQ)
                .build();
    }

    /**
     * Dead Letter Queue — очередь для сообщений, которые не удалось обработать.
     *
     * В промышленных системах DLQ мониторится: если в ней появились сообщения —
     * это инцидент, требующий расследования. Без DLQ «битые» сообщения просто теряются.
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(ORDER_DLQ)
                .build();
    }

    /**
     * Привязка основной очереди к topic exchange.
     *
     * Binding key "#" означает «все сообщения» — product-service фиксирует всё.
     *
     * В продакшене можно создать несколько очередей с разными binding key:
     * - q.product.books с "book.*" — только события книг,
     * - q.notification.authors с "author.created" — уведомления при создании автора.
     */
    @Bean
    public Binding productBinding(Queue productQueue, TopicExchange eventsExchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(eventsExchange)
                .with(RoutingKeys.ALL_EVENTS);
    }

    /**
     * Привязка DLQ к dead letter exchange.
     */
    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with(ORDER_DLQ);
    }
}
