package feign.cf.feign;


import feign.cc.FastjsonDecoder;
import feign.cc.FastjsonEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;

public class FastjsonFeignConfiguration implements FeignConfigurationBeansProvider {
    @Bean
    public Decoder decoder() {
        return (Decoder)new FastjsonDecoder();
    }

    @Bean
    public Encoder encoder() {
        return (Encoder)new FastjsonEncoder();
    }
}
