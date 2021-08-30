package feign.cf.feign;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.cc.ChainEncoder;
import feign.cc.GetMethodUseObjectAsQueryParamsEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;

public class JacksonFeignConfiguration implements FeignConfigurationBeansProvider {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule((Module)new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    @Bean
    public Decoder decoder() {
        return (Decoder)new JacksonDecoder(objectMapper);
    }

    @Bean
    public Encoder encoder() {
        return (Encoder)(new ChainEncoder())
                .addEncoder((Encoder)new JacksonEncoder(objectMapper))
                .addEncoder((Encoder)new GetMethodUseObjectAsQueryParamsEncoder());
    }
}
