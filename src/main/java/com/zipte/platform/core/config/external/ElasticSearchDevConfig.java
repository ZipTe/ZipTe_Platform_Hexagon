package com.zipte.platform.core.config.external;

import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
@Profile("dev")
@EnableElasticsearchRepositories(basePackages = "com.zipte.platform.server.adapter.out.external.elk")
public class ElasticSearchDevConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.user_name}")
    private String username;

    @Value("${elasticsearch.user_password}")
    private String password;

    @Value("${elasticsearch.ca_cert_path}")
    private String caCertPath;

    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new File(caCertPath))
                    .build();

            return ClientConfiguration.builder()
                    .connectedTo(host + ":" + port)
                    .usingSsl(sslContext)
                    .withBasicAuth(username, password)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to configure Elasticsearch SSL context", e);
        }
    }
}
