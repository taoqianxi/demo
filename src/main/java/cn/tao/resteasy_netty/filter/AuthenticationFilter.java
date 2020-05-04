package cn.tao.resteasy_netty.filter;


import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @author xiaoming
ze * @create 2017-8-31 11:18:03
 */
@Component
@Provider
public class AuthenticationFilter implements ContainerResponseFilter {

	@Context
	ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext,ContainerResponseContext cres) throws IOException {
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization,token");
		cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	}


}

