package cn.tao;

import cn.tao.util.QRCodeUtil;

import java.util.*;

public class Test {
        public static void main(String[] args) {
            Map<String,String> map = new HashMap<>();
            map.put("2","key2");
            map.put("1","key1");
            Set<Map.Entry<String, String>> entries = map.entrySet();
            try {
                QRCodeUtil.encode("http://2784945ew6.zicp.vip/pages/index.html","C:\\Users\\Admin\\Pictures\\Saved Pictures",true);
            } catch (Exception e) {
                e.printStackTrace();
            }


//            for (Map.Entry<String, String> entry : entries) {
//                System.err.println(entry.getKey());
//                System.out.println(entry.getValue());
//            }
//            List<String> list = new ArrayList<>();
//            list.add("2");
//            list.add("1");
//            for (String s : list) {
//                System.err.println(s);
//            }
        }
}
