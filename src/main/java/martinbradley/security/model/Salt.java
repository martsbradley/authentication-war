package martinbradley.security.model;

public class Salt {
    private String saltValue;

    public Salt(String saltValue) {
        this.saltValue = saltValue;
    }

    public String getSaltValue() {
        return saltValue;
    }
}
