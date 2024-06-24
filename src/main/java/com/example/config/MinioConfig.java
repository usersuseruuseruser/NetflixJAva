package com.example.config;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class MinioConfig {
    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.external-url}")
    private String externalUrl;

    @Value("${minio.external-port}")
    private String externalPort;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(externalUrl, Integer.parseInt(externalPort), false)
                .credentials(accessKey, secretKey)
                .httpClient(new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url,9000))).build())
                .build();
    }
}