package cn.tao.resteasy_netty.filter;

import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class OptionsHandler implements ExceptionMapper<DefaultOptionsMethodException> {

    private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN_ANYONE = "*";

    @Context
    HttpHeaders httpHeaders;

    @Override
    public Response toResponse(DefaultOptionsMethodException e) {
        final Response.ResponseBuilder response = Response.ok();

        String requestHeaders = httpHeaders.getHeaderString(ACCESS_CONTROL_REQUEST_HEADERS);
        String requestMethods = httpHeaders.getHeaderString(ACCESS_CONTROL_REQUEST_METHOD);

        if (requestHeaders != null)
            response.header(ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);

        if (requestMethods != null)
            response.header(ACCESS_CONTROL_ALLOW_METHODS, requestMethods);

        response.header(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_ANYONE);

        return response.build();
    }
}
