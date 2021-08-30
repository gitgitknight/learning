package feign.cf;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.cf.feign.FastjsonFeignConfiguration;
import feign.cf.feign.FeignConfigurationBeansProvider;
import feign.cf.feign.JacksonFeignConfiguration;
import feign.cf.feign.MultipartFileSupportFeignConfiguration;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties({FeignProperties.class})
public class FeignAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FeignAutoConfiguration.class);

    private final FeignConfigurationBeansProvider feignConfigurationBeansProvider;

    @Autowired
    public FeignAutoConfiguration(FeignProperties feignProperties, ObjectFactory<HttpMessageConverters> messageConverters) {
        log.info("Initialize multipart file configuration");
        log.info(String.format("Use %s as Feign codec.", new Object[] { feignProperties.getCodec().name() }));
        switch (feignProperties.getCodec()) {
            case jackson:
                this.feignConfigurationBeansProvider = (FeignConfigurationBeansProvider)new JacksonFeignConfiguration();
                return;
            case fastjson:
                this.feignConfigurationBeansProvider = (FeignConfigurationBeansProvider)new FastjsonFeignConfiguration();
                return;
            case file:
                this.feignConfigurationBeansProvider = (FeignConfigurationBeansProvider)new MultipartFileSupportFeignConfiguration(messageConverters);
                return;
        }
        this.feignConfigurationBeansProvider = (FeignConfigurationBeansProvider)new JacksonFeignConfiguration();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnMissingBean({SimpleRequestHeadersFilter.class})
    SimpleRequestHeadersFilter simpleRequestHeadersFilter() {
        return SimpleRequestHeadersFilter.create();
    }

    @Bean
    @ConditionalOnMissingBean({Logger.Level.class})
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @ConditionalOnMissingBean({ErrorDecoder.class})
    public ErrorDecoder errorDecoder() {
        return this.feignConfigurationBeansProvider.errorDecoder();
    }

    @Bean
    @ConditionalOnMissingBean({Decoder.class})
    public Decoder decoder() {
        return this.feignConfigurationBeansProvider.decoder();
    }

    @Scope("prototype")
    @Bean
    @ConditionalOnMissingBean({Encoder.class})
    public Encoder encoder() {
        return this.feignConfigurationBeansProvider.encoder();
    }

    @Bean
    @ConditionalOnMissingBean({Retryer.class})
    public Retryer feignRetryer() {
        return this.feignConfigurationBeansProvider.feignRetryer();
    }

    @Bean
    @ConditionalOnMissingBean({Contract.class})
    public Contract contract() {
        return this.feignConfigurationBeansProvider.contract();
    }

    @Bean
    @ConditionalOnMissingBean({RequestInterceptor.class})
    public RequestInterceptor requestInterceptor() {
        return this.feignConfigurationBeansProvider.requestInterceptor();
    }
}
