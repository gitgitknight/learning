package feign.cc;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import java.lang.reflect.Type;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadFeignSpringEncoder extends SpringFormEncoder {
    public FileUploadFeignSpringEncoder(Encoder encoder) {
        super(encoder);
    }

    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        super.encode(object, bodyType, template);
        if (bodyType.equals(MultipartFile.class) || bodyType.equals(MultipartFile.class) || (bodyType
                .toString() != null && bodyType.toString().equals("org.springframework.util.MultiValueMap<java.lang.String, java.lang.Object>")))
            template.body(template.body(), null);
    }
}
