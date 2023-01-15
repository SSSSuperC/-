public class Admin {
    int id;
    String username;
    String password;
    String identity;
    int rank;
    Admin()
    {
        this(000000,"user","123456","assistant");
    }
    Admin(int id,String username,String password,String identity) {
        this.id=id;
        this.username = new String(username);
        this.password = new String(password);
        this.identity = new String(identity);
        if (identity=="admin") {
            this.rank=1;
        }else if (identity=="manager") {
            this.rank=2;
        }else if (identity=="boss") {
            this.rank=3;
        }else this.rank=0;
    }
    void setId(int id)
    {
        this.id = id;
    }
    void setUsername(String username) {
        this.username = username;
    }
    void setPassword(String password) {
        this.password = password;
    }
    void setIdentity(String identity) {
        this.identity = identity;
        if (identity=="admin") {
            this.rank=1;
        }else if (identity=="manager") {
            this.rank=2;
        }else if (identity=="boss") {
            this.rank=3;
        }else this.rank=0;
    }
    int getId()
    {
        return this.id;
    }
    String getUsername()
    {
        return this.username;
    }
    String getPassword()
    {
        return this.password;
    }
    String getIdentity() {
        return this.identity;
    }
}
