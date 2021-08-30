package feign.cc;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChainEncoder implements Encoder {
    private List<Encoder> encoders = new ArrayList<>();

    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        this.encoders.forEach(encoder -> encoder.encode(o, type, requestTemplate));
    }

    public ChainEncoder addEncoder(Encoder encoder) {
        this.encoders.add(encoder);
        return this;
    }
}
