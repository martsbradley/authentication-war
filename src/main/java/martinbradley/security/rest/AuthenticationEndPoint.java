package martinbradley.security.rest;

import martinbradley.security.CookieHandler;
import martinbradley.security.rest.UserPassword;
import martinbradley.security.api.AuthenticationHandler;
import martinbradley.security.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/verifyuser")
public class AuthenticationEndPoint {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationEndPoint.class);

    @Inject
    AuthenticationHandler authenticationHandler;

    @GET
    @Produces("application/txt")
    public String hi(UserPassword userDetails) {
        return "hi there";
    }

    @POST
    @Produces("application/json")
    public Response authenticate(UserPassword userDetails) {
        logger.debug("authenticate " + userDetails);

        List<NewCookie> cookies = Collections.emptyList();

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setUsername(userDetails.getUsername());
        authResponse.setUserAuthenticated(false);
        // needs to check the database and create a JWT token if the user is valid.
        try {
            JsonWebToken jasonWebToken = authenticationHandler.authenticate(userDetails);
            CookieHandler ch = new CookieHandler();
            cookies = ch.handleSuccessfulLogin(jasonWebToken);
        } catch (Exception e) {
            logger.warn("Access denied for " + userDetails);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        authResponse.setUserAuthenticated(true);
        NewCookie[] cookieArr = new NewCookie[cookies.size()];
        cookieArr = cookies.toArray(cookieArr);

        return Response.accepted(authResponse)
                .type(MediaType.APPLICATION_JSON)
                .cookie(cookieArr)
                .build();
    }

    @DELETE
    @Produces("application/json")
    public Response logout(UserPassword userDetails){
        logger.info("logout called ");
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setUserAuthenticated(false);

        CookieHandler ch = new CookieHandler();
        List<NewCookie> cookies = ch.clearCookies();
        NewCookie[] cookieArr = new NewCookie[cookies.size()];
        cookieArr = cookies.toArray(cookieArr);

        logger.info("logout resetting cookies");
        return Response.accepted(authResponse)
                .type(MediaType.APPLICATION_JSON)
                .cookie(cookieArr)
                .build();
    }
}
