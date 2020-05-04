package cn.tao.netty.Service;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpParamConvert {

    public Map<String,String> paramToString(String param) throws Exception{
        Map<String,String> paramMap = new HashMap<>();
        if (!StringUtils.isEmpty(param)) {
            String[] split = param.split("=|&");
            int index = split.length;
            if (index % 2 !=0) {
                throw new RuntimeException("参数异常！！");
            }
            int i = 0;
            while (true){
                paramMap.put(split[i],split[i+1]);
                i +=2;
                if (index <= i) {
                    break;
                }
            }
        }
        return paramMap;
    }
}
