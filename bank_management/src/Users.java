import java.sql.Connection;

class Users
{
    private int id;
    private String username;
    private String password;
    Users()
    {
        this("user","123456");
    }
    Users(String username,String password)
    {
        this.username = new String(username);
        this.password = new String(password);
    }
    void setId(int id)
    {
        this.id = id;
    }
    void setUsername(String username)
    {
        this.username = username;
    }
    void setPassword(String password)
    {
        this.password = password;
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
        return  this.password;
    }
}
