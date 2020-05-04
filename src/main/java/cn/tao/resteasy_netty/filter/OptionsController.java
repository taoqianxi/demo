package cn.tao.resteasy_netty.filter;

import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RestController
@Path("{path:.*}")
public class OptionsController {

    @OPTIONS
    @Path("{path:.*}")
    public Response handleCORSRequest() throws Exception {
        Response.ResponseBuilder builder = Response.ok();
        return builder.build();
    }
}
