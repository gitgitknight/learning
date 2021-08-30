package feign.cf;

import feign.da.Codec;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ev.feign")
public class FeignProperties {
    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    private Codec codec = Codec.jackson;

    public Codec getCodec() {
        return this.codec;
    }
}
