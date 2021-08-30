package feign;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    private static final Logger log = LoggerFactory.getLogger(CustomFeignErrorDecoder.class);

    final ErrorDecoder defaultDecoder = (ErrorDecoder)new ErrorDecoder.Default();

    private Decoder decoder;

    public CustomFeignErrorDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Exception decode(String methodKey, Response response) {
        return decodeResponseBean(methodKey, response);
    }

    private Exception decodeResponseBean(String methodKey, Response response) {
        log.error("Feign{}", response.toString());
        if (response.status() >= 400 && response.status() <= 499)
            try {
                ApiResponseBean<String> responseBean = (ApiResponseBean<String>)this.decoder.decode(response, ApiResponseBean.class);
                return (Exception)new RpcBadRequestException(responseBean, Integer.valueOf(response.status()), methodKey);
            } catch (Exception e) {
                log.error("", e);
                return this.defaultDecoder.decode(methodKey, response);
            }
        return this.defaultDecoder.decode(methodKey, response);
    }
}
