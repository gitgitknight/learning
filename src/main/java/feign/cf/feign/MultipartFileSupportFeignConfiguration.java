package feign.cf.feign;


import feign.cc.ChainEncoder;
import feign.cc.FileUploadFeignSpringEncoder;
import feign.cc.GetMethodUseObjectAsQueryParamsEncoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

public class MultipartFileSupportFeignConfiguration extends JacksonFeignConfiguration {
    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public MultipartFileSupportFeignConfiguration(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder encoder() {
        return (Encoder)(new ChainEncoder())
                .addEncoder((Encoder)new FileUploadFeignSpringEncoder((Encoder)new SpringEncoder(this.messageConverters)))
                .addEncoder((Encoder)new GetMethodUseObjectAsQueryParamsEncoder());
    }
}
