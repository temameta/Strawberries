package org.strawberries.productservice.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.strawberries.productservice.service.GetPriceServiceImpl;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcServerLifecycle implements SmartLifecycle {
    @Value("${grpc.server.port:9091}")
    private int grpcPort;
    private Server server;
    private boolean running = false;
    private final GetPriceServiceImpl getPriceService;

    @Override
    public void start() {
        try {
            server = ServerBuilder.forPort(grpcPort)
                    .addService(getPriceService)
                    .build()
                    .start();

            running = true;
            log.info("gRPC-сервер запущен на порту {}", grpcPort);
            log.info("Сервис: GetPriceServiceGrpc.GetPriceServiceImplBase");

        } catch (IOException e) {
            throw new RuntimeException("Не удалось запустить gRPC-сервер на порту " + grpcPort, e);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            log.info("Остановка gRPC-сервера...");
            server.shutdown();
            running = false;
            log.info("gRPC-сервер остановлен");
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
