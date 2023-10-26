package com.lizhisdk;

import com.lizhisdk.client.LizhiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 */
@Configuration
@ConfigurationProperties("yuapi.client")
@Data
@ComponentScan
public class LizhiClientSdkConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LizhiClient yuApiClient() {
        return new LizhiClient(accessKey, secretKey);
    }

}
