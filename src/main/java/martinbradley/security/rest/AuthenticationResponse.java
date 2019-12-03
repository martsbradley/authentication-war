package martinbradley.security.rest;

import javax.inject.Named;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Named
@XmlRootElement(name="AuthenticationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticationResponse implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Size(min=8, max=20)
    @XmlElement
    private String username = "";
    private boolean userAuthenticated = false;
    public AuthenticationResponse() {

    }

    public AuthenticationResponse(String username, boolean userAuthenticated) {

        this.username = username;
        this.userAuthenticated = userAuthenticated;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUserAuthenticated() {
        return userAuthenticated;
    }

    public void setUserAuthenticated(boolean userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

    @Override
    public String toString()
    {
        return "UserPassword [" + username + "]";
    }
}
