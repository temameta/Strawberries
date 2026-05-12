package org.strawberries.orderevents;

public final class RoutingKeys {

    private RoutingKeys() {}

    // Имя общего topic exchange для доменных событий
    public static final String EXCHANGE = "orders.events";

    // Routing keys для событий заказов
    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_DELETED = "order.deleted";

    // Паттерны для подписки (wildcard)
    public static final String ALL_ORDER_EVENTS = "book.*";
    public static final String ALL_EVENTS = "#";
}
