package feign.cc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;

public class FastjsonEncoder implements Encoder {
    private SerializeConfig config = null;

    public FastjsonEncoder() {
        this(null);
    }

    public FastjsonEncoder(SerializeConfig config) {
        if (null != config) {
            this.config = config;
        } else {
            this.config = SerializeConfig.getGlobalInstance();
        }
    }

    public void encode(Object obj, Type type, RequestTemplate template) throws EncodeException {
        template.body(JSON.toJSONString(obj, this.config, new com.alibaba.fastjson.serializer.SerializerFeature[0]));
    }
}
