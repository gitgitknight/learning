package feign.cf.feign;


import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

public interface FeignConfigurationBeansProvider {
    @Bean
    Decoder decoder();

    @Bean
    Encoder encoder();

    @Bean
    default Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    default ErrorDecoder errorDecoder() {
        return (ErrorDecoder)new CustomFeignErrorDecoder(decoder());
    }

    @Bean
    default Contract contract() {
        return (Contract)new SpringMvcContract();
    }

    @Bean
    default Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    default RequestInterceptor requestInterceptor() {
        return (RequestInterceptor)new CustomFeignRequestInterceptor();
    }

    default Feign.Builder feignBuilder() {
        return Feign.builder()
                .contract(contract())
                .decoder(decoder())
                .encoder(encoder())
                .errorDecoder(errorDecoder())
                .requestInterceptor(requestInterceptor())
                .logLevel(feignLoggerLevel())
                .retryer(feignRetryer());
    }
}
