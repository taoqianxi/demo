package cn.tao.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.security.rsa.RSASignature;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static void main(String[] args) {
        JwtBuilder builder =Jwts.builder();
        Map<String ,Object> map  =new HashMap<>();
        map.put("userName","小明");
        map.put("passWord", "123456");
        Date date = new Date();
        date.setTime(1577588036000l);
        builder.setClaims(map)
                .setIssuedAt(new Date())//设置签发日期 //必须在这个时间之后才可以用
                .signWith(SignatureAlgorithm.HS256,"hahaha")
                .setNotBefore(date);//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        System.out.println(builder.compact());

        Claims hahaha = Jwts
                .parser()
                .setSigningKey("hahaha")
                .parseClaimsJws(builder.compact())
                .getBody();
        System.err.println(hahaha);

    }
}
