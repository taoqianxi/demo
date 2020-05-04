package cn.tao.controller;

import cn.tao.entity.Test;
import cn.tao.mapper.TestMapper;
import cn.tao.util.test.PPTtoPNG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.lang.model.type.TypeMirror;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("test")
public class TestController {
    @Autowired
    private TestMapper testMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tao")
    public List<Test> tao() {
//        PPTtoPNG ppTtoPNG = new PPTtoPNG();
//        synchronized (testMapper){
//            ppTtoPNG.test();
//        }
        return testMapper.listTest();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public String test(String name) {
        PPTtoPNG ppTtoPNG = new PPTtoPNG();
        synchronized (testMapper){
            ppTtoPNG.test();
        }
        return name;
    }
}
