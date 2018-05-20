package com.clsaa.ms.hermes.config;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 任贵杰
 * @version v1
 * @summary
 * @since 2018/5/20
 */
@Getter
@Setter
@SpringBootConfiguration
@ConfigurationProperties(prefix = "alibaba.cloud.open.search")
public class AliOpenSearch {
  private String accessKey;
  private String accessSecret;
  private String host;
  private String appName;

  @Primary
  @Bean("alibaba.cloud.open.searchClient")
  public OpenSearchClient createCloudSearchClient() {
    OpenSearch openSearch = new OpenSearch(accessKey,accessSecret,host);
    return new OpenSearchClient(openSearch);
  }
}
