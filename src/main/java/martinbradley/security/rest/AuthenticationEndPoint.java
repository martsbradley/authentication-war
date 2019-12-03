package martinbradley.security.rest;

import martinbradley.security.CookieHandler;
import martinbradley.security.rest.UserPassword;
import martinbradley.security.api.AuthenticationHandler;
import martinbradley.security.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/verifyuser")
public class AuthenticationEndPoint
{
    private static Logger logger = LoggerFactory.getLogger(AuthenticationEndPoint.class);

    @Inject
    AuthenticationHandler authenticationHandler;

    @POST
    @Produces("application/json")
    public Response authenticate(UserPassword userDetails)
    {
        logger.debug("authenticate " + userDetails);

        List<NewCookie> cookies = Collections.emptyList();
        // needs to check the database and create a JWT token if the user is valid.
        try {
            JsonWebToken jasonWebToken = authenticationHandler.authenticate(userDetails);
            CookieHandler ch = new CookieHandler();
            cookies = ch.handleSuccessfulLogin(jasonWebToken);
        }
        catch (Exception e) {
            logger.warn("Access denied for " + userDetails);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        NewCookie[] cookieArr = new NewCookie[cookies.size()];
        cookieArr = cookies.toArray(cookieArr);

        return Response.accepted()
                       .type(MediaType.APPLICATION_JSON)
                       .cookie(cookieArr)
                       .build();
    }
}
