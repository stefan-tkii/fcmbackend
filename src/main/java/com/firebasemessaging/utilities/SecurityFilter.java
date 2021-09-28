package com.firebasemessaging.utilities;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter 
{

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURED_URL_PREFIX = "messaging";

    private static final String ADMIN_ID = "Admin";
    private static final String ADMIN_PASS = "94574549873d9b2a51ff7f4cd009f6d4257d7cfa9910023db546dbc0b872545f12";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException 
    {
        if(requestContext != null)
        {
            if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
            {
                List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
                if((authHeader != null))
                {
                    if(authHeader.size() > 0)
                    {
                        String authToken = authHeader.get(0);
                        authToken = authToken.replace(AUTHORIZATION_HEADER_PREFIX, "");
                        String decoded = Base64.decodeAsString(authToken);
                        StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
                        String userId = tokenizer.nextToken();
                        String token = tokenizer.nextToken();
                        if(!(userId.equals(ADMIN_ID) && token.equals(ADMIN_PASS)))
                        {
                            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You must provide a valid ID and authorization token to access this resource.").build();
                            requestContext.abortWith(unauthorizedStatus);
                        }
                    }
                    else
                    {
                        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You must provide your ID and authorization token to access this resource.").build();
                        requestContext.abortWith(unauthorizedStatus);
                    }
                }
                else
                {
                    Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You must provide your ID and authorization token to access this resource.").build();
                    requestContext.abortWith(unauthorizedStatus);
                }
            }
        }
    }
    
}