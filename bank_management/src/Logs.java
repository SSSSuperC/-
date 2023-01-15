import java.text.SimpleDateFormat;
import java.util.Date;

class Logs {
    private Date date;
    private Double monetary;
    private Double balance_after;

    //    private String target_account;
    void Logs() {

    }

    String deposit(double _balance, double _monetary) {
        this.date = new Date();//保存交易时间
        this.monetary = _monetary;//封装交易金额
        this.balance_after = _balance + _monetary;//封装交易后的金额
        return getString(this.date) + "     存款" + this.monetary.toString() + "元," + "账户余额" + this.balance_after;
    }

    String transfer(double _balance, double _monetary, String _target_account) {
        this.date = new Date();//保存交易时间
        this.monetary = _monetary;//封装交易金额
        this.balance_after = _balance - _monetary;//封装交易后的金额
        return getString(this.date) + "     转账" + this.monetary.toString() + "元到账户" + _target_account + ",账户余额" + this.balance_after;
    }

    String draw(double _balance, double _monetary) {
        this.date = new Date();//保存交易时间
        this.monetary = _monetary;//封装交易金额
        this.balance_after = _balance - _monetary;//封装交易后的金额
        return getString(this.date) + "     取款" + this.monetary.toString() + "元," + "账户余额" + this.balance_after;
    }

    public String getString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(date);
        return format;
    }
}


