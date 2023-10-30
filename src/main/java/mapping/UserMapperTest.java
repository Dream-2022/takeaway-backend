package mapping;

import javax.management.Query;

public class UserMapperTest {
    private String password;
    private String account;

    public UserMapperTest(String password, String account) {
        this.password = password;
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
