package martinbradley.security.api;

import martinbradley.security.JsonWebToken;

import javax.naming.AuthenticationException;

public interface AuthenticationBroker {
    JsonWebToken authenticate(String userName, String password) throws AuthenticationException;
}
