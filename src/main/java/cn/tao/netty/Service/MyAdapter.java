package cn.tao.netty.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.dataformat.xml.util.AnnotationUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Component
public class MyAdapter {

    private String dataJson;
    public String adapter(String url, ApplicationContext applicationContext) throws Exception {
        String decode = URLDecoder.decode(url, "utf-8");
        String[] split = decode.split("\\?");
        String path = split[0];
        HttpParamConvert convert = new HttpParamConvert();
        Map<String, String> paramMap = convert.paramToString(split[1]);

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        Set<Map.Entry<String, Object>> annotationEntrys = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> annotationEntry : annotationEntrys) {
            Class<?> aClass = annotationEntry.getValue().getClass();
            Object bean = applicationContext.getBean(aClass);
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                String value = method.getAnnotation(Path.class).value().replaceAll("/","");
                if (path.replaceAll("/","").equals(value)) {
                    //开始提取方法的参数
                    Object invoke = method.invoke(bean,123);
                    dataJson = JSON.toJSONString(invoke);
                    return dataJson;
                }
            }
        }
        return dataJson;
    }
}
