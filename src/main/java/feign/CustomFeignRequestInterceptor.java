package feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFeignRequestInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(CustomFeignRequestInterceptor.class);

    public void apply(RequestTemplate requestTemplate) {
        log.debug("- Feign");
                String tokenStr = RequestHeadersManager.getAuthorization();
        if (requestTemplate.headers().get("Authorization") == null) {
            log.debug(String.format("\t- %s:%s", new Object[] { "Authorization", tokenStr }));
            requestTemplate.header("Authorization", new String[] { tokenStr });
        }
        log.debug(String.format("\t- %s:%s", new Object[] { "t_id", RequestHeadersManager.getTid() }));
        requestTemplate.header("t_id", new String[] { RequestHeadersManager.getTid() });
        if (requestTemplate.headers().get("Accept") == null && requestTemplate.headers().get("accept") == null) {
            log.debug("Header `Accept` was not specified, fallback to default value: application/json;charset=UTF-8");
            requestTemplate.header("Accept", new String[] { "application/json;charset=UTF-8" });
        }
        if (requestTemplate.headers().get("Content-Type") == null && requestTemplate
                .headers().get("content-type") == null &&

                !"GET".equalsIgnoreCase(requestTemplate.method())) {
            log.debug("Header `Content-Type` was not specified, fallback to default value: application/json;charset=UTF-8");
            requestTemplate.header("Content-Type", new String[] { "application/json;charset=UTF-8" });
        }
    }
}
