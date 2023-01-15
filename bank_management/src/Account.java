class Account extends Users
{
    private Users user;
    private double pre_assets;
    private double now_assets;
    private double balance;
    Account()
    {
        this(new Users(),0,0,0);
    }
    Account(Users user,double pre_assets , double now_assets,double balance)
    {
        this.user = user;
        this.pre_assets = pre_assets;
        this.now_assets = now_assets;
        this.balance = balance;
    }
    void setUser(Users user)
    {
        this.user = user;
    }
    void setPre_assets(double pre_assets)
    {
        this.pre_assets = pre_assets;
    }
    void setNow_assets(double now_assets)
    {
        this.now_assets = now_assets;
    }
    void setBalance(double balance)
    {
        this.balance = balance;
    }
    Users getUser()
    {
        return this.user;
    }
    double getPre_assets()
    {
        return this.pre_assets;
    }
    double getNow_assets()
    {
        return this.now_assets;
    }
    double getBalance()
    {
        return this.balance;
    }
}
