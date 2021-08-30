package feign.cc;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import java.util.Map;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class GetMethodUseObjectAsQueryParamsEncoder implements Encoder {
    private static final Logger log = LoggerFactory.getLogger(GetMethodUseObjectAsQueryParamsEncoder.class);

    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        if (request.method().equals(HttpMethod.GET.name()) && requestBody != null) {
            log.warn("Request body is use as GET query params, try extract it into params");
            log.warn("Remove request body");
            request.body(null);
            Map<String, Object> requestMap = convertObjToMap(requestBody);
            for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
                if (null != entry.getValue())
                    request.query(entry.getKey().toString(), new String[] { entry.getValue().toString() });
            }
        }
    }

    private Map<String, Object> convertObjToMap(Object obj) {
        if (obj == null)
            return null;
        return (Map<String, Object>)new BeanMap(obj);
    }
}
