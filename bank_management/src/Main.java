import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.CookieHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application
{
    public void start(Stage primarystage)
    {
        //主菜单
        Scene mainscene = mainmenu(primarystage);
        //主菜单
        primarystage.setTitle("银行模拟系统");
        primarystage.setScene(mainscene);
        primarystage.show();
    }

    //连接数据库
    public Connection Connect_Sqlserver()
    {
        Connection ct = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=bank_management","sa", "011202");
            //System.out.println("连接数据库成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.print("连接失败");
        }
        return ct;
    }

    //查询普通用户名数据库
    public ArrayList <String> Search_Sqlserver(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <String > rt = new ArrayList<>(0);
        try {
            String quiry = new String("SELECT acc_num FROM Users WHERE acc_num = '" + username + "'");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            while(rs.next())
            {
                //System.out.println(rs.getString(1));
                rt.add(rs.getString(1));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询普通用户名和密码数据库
    public ArrayList <String> Search_Login(Connection ct,String username,String password)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <String > rt = new ArrayList<>(0);
        try {
            String quiry = new String("SELECT Users.acc_num FROM Users,Account WHERE Account.acc_num = '" + username + "' AND Users.acc_num = '" + username + "' AND acc_pass = '" + password +"'");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            while(rs.next())
            {
                //System.out.println(rs.getString(1));
                rt.add(rs.getString(1));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询职员用户名和密码数据库
    public ArrayList <String> Search_LoginStaff(Connection ct,String username,String password)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <String > rt = new ArrayList<>(0);
        try {
//            System.out.println(username);
            String quiry = new String("SELECT acc_num FROM Admins WHERE acc_num = '" + username + "' AND acc_pass = '" + password +"'");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            while(rs.next())
            {
                //System.out.println(rs.getString(1));
                rt.add(rs.getString(1));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    public void insert_Sqlserver(Connection ct,String username, String password , String name , String sex, String tel)
    {
        Date date = new Date();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <String > rt = new ArrayList<>();
        try
        {
            String quiry = new String("SELECT max(id) from users");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            rs.next();
            int UID = rs.getInt(1);
            UID++;
            String insert_username = new String("INSERT INTO Users values('" + UID + "' , '" + name + "' , '" + sex + "' , '" + tel + "' , '" + username + "' , '" + password + "')");
            ps = ct.prepareStatement(insert_username);
            ps.executeUpdate();
            PreparedStatement ps1 = null;
            String insert = new String("INSERT INTO Account values('" + username + "','" + name + "','" + date.toString() + "','" + 0 + "','" + 0 + "','" + 0 +"','" + 1 +"')");
            ps1 = ct.prepareStatement(insert);
            ps1.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void insert_Staff(Connection ct,String username, String password , String name , String sex, String tel ,String rank)
    {
        Date date = new Date();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <String > rt = new ArrayList<>();
        try
        {
            String quiry = new String("SELECT max(id) from users");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            rs.next();
            int SID = rs.getInt(1);
            SID++;
            String insert_username = new String("INSERT INTO Users values('" + SID + "' , '" + name + "' , '" + sex + "' , '" + tel + "' , '" + username + "' , '" + password + "')");
            ps = ct.prepareStatement(insert_username);
            ps.executeUpdate();
            PreparedStatement ps1 = null;
            String insert = new String("INSERT INTO Admins values('" + SID + "' , '" + name + "' , '" + sex + "' , '" + tel + "' , '" + username + "' , '" + password + " ',' " + rank + "')");
            ps1 = ct.prepareStatement(insert);
            ps1.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //用户注册函数
    public void Register(Connection ct,Stage primarystage)
    {
        //信息输入框
        TextField accountname = new TextField();
        TextField password = new TextField();
        TextField testfield = new TextField();
        TextField nametf = new TextField();
        TextField sextf = new TextField();
        TextField teltf = new TextField();
        //标签框
        String testnum [] = Testnum();
        Label anlabel = new Label("账户名");
        Label pwlabel = new Label("密码");
        Label tflabel = new Label("验证码(均为整数)" + testnum[0]);
        Label namelb = new Label("姓名");
        Label sexlb = new Label("性别");
        Label tellb = new Label("电话");
        anlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        pwlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        namelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        sexlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tellb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setText("验证码(均为整数)" + testnum[0]);
        Label test = new Label(testnum[1]);
        //确认按钮
        Button bt = new Button("确认");
        bt.setOnAction(
                e->
                {
                    if(check_Register(accountname.getText(),password.getText(),testfield.getText(),testnum[1],ct))
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("注册成功");
                        alert.setContentText("恭喜您注册成功!");
                        alert.show();
                        insert_Sqlserver(ct,accountname.getText(),password.getText(),nametf.getText(),sextf.getText(),teltf.getText());
                        primarystage.setTitle("银行模拟系统");
                        primarystage.setScene(mainmenu(primarystage));

                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("注册信息有误");
                        alert.setContentText("注册信息有误,请重新注册!");
                        alert.show();
                        tflabel.setText("验证码(均为整数)" + testnum[0]);
                        test.setText(testnum[1]);
                    }
                }
        );
        Button returnbt = new Button("返回");
        returnbt.setOnAction(event ->
        {
            try
            {
                primarystage.setTitle("银行模拟系统");
                primarystage.setScene(mainmenu(primarystage));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });


        //Gridpane构架
        GridPane register_menu = new GridPane();
        register_menu.add(accountname,1,0);
        register_menu.add(password,1,2);
        register_menu.add(testfield,1,10);
        register_menu.add(nametf,1,6);
        register_menu.add(sextf,1,8);
        register_menu.add(teltf,1,4);
        register_menu.add(anlabel,0,0);
        register_menu.add(namelb,0,6);
        register_menu.add(sexlb,0,8);
        register_menu.add(tellb,0,4);
        register_menu.add(pwlabel,0,2);
        register_menu.add(tflabel,0,10);
        // register_menu.add(test,0,12);
        register_menu.add(bt,1,13);
        register_menu.add(returnbt,2,13);
        register_menu.setAlignment(Pos.CENTER);
        //背景图片
        StackPane register_background = new StackPane(register_menu);
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        register_background.setBackground(bGround);//设置背景图片
        //新scene
        Scene register_scene = new Scene(register_background,700,700);
        primarystage.setScene(register_scene);
        primarystage.setTitle("注册界面");
        primarystage.show();
    }

    //员工注册函数
    public void Register_Staff(Connection ct,Stage primarystage,String username)
    {
        //信息输入框
        TextField accountname = new TextField();
        TextField password = new TextField();
        TextField testfield = new TextField();
        TextField nametf = new TextField();
        TextField sextf = new TextField();
        TextField teltf = new TextField();
        TextField ranktf = new TextField();
        //标签框
        String testnum [] = Testnum();
        Label anlabel = new Label("账户名");
        Label pwlabel = new Label("密码");
        Label tflabel = new Label("验证码(均为整数)" + testnum[0]);
        Label namelb = new Label("姓名");
        Label sexlb = new Label("性别");
        Label tellb = new Label("电话");
        Label ranklb = new Label("级别");
        anlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        pwlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        namelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        sexlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tellb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        ranklb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setText("验证码(均为整数)" + testnum[0]);
        Label test = new Label(testnum[1]);
        //确认按钮
        Button bt = new Button("确认");
        bt.setOnAction(
                e->
                {
                    if(check_Register(accountname.getText(),password.getText(),testfield.getText(),testnum[1],ct))
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("注册成功");
                        alert.setContentText("恭喜您注册成功!");
                        alert.show();
                        insert_Staff(ct,accountname.getText(),password.getText(),nametf.getText(),sextf.getText(),teltf.getText(),ranktf.getText());
                        primarystage.setScene(mainmenu(primarystage));
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("注册信息有误");
                        alert.setContentText("注册信息有误,请重新注册!");
                        alert.show();
                        tflabel.setText("验证码(均为整数)" + testnum[0]);
                        test.setText(testnum[1]);
                    }
                }
        );
        Button returnbt = new Button("返回");
        returnbt.setOnAction(event ->
        {
            try
            {
                manager_View(ct,primarystage,username);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        //Gridpane构架
        GridPane register_menu = new GridPane();
        register_menu.add(accountname,1,0);
        register_menu.add(password,1,2);
        register_menu.add(testfield,1,12);
        register_menu.add(nametf,1,6);
        register_menu.add(sextf,1,8);
        register_menu.add(teltf,1,4);
        register_menu.add(anlabel,0,0);
        register_menu.add(namelb,0,6);
        register_menu.add(sexlb,0,8);
        register_menu.add(tellb,0,4);
        register_menu.add(pwlabel,0,2);
        register_menu.add(tflabel,0,12);
        register_menu.add(ranklb,0,10);
        register_menu.add(ranktf,1,10);
        //register_menu.add(test,0,14);
        register_menu.add(bt,1,15);
        register_menu.add(returnbt,2,15);
        register_menu.setAlignment(Pos.CENTER);
        //背景图片
        StackPane register_background = new StackPane(register_menu);
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        register_background.setBackground(bGround);//设置背景图片
        //新scene
        Scene register_scene = new Scene(register_background,700,700);
        primarystage.setScene(register_scene);
        primarystage.setTitle("职员注册界面");
        primarystage.show();
    }

    //验证码生成函数
    public  String [] Testnum()
    {
        String [] rt = new String[2];
        int symbol = (int)(Math.random()*4);
        int num1,num2;
        switch (symbol)
        {
            case 0:
                num1 = (int)(Math.random()*10);
                num2 = (int)(Math.random()*10);
                rt[0] = num1 + " + " + num2;
                rt[1] = new String(new Integer(num1 + num2).toString());
                break;
            case 1:
                num1 = (int)(Math.random()*10);
                num2 = (int)(Math.random()*10);
                rt[0] = num1 + " - " + num2;
                rt[1] = new String(new Integer(num1 - num2).toString());
                break;
            case 2:
                num1 = (int)(Math.random()*10);
                num2 = (int)(Math.random()*10);
                rt[0] = num1 + " * " + num2;
                rt[1] = new String(new Integer(num1 * num2).toString());
                break;
            default:
                num1 = (int)(Math.random()*10);
                num2 = (int)(Math.random()*10 + 1);
                rt[0] = num1 + " / " + num2;
                rt[1] = new String(new Integer(num1 / num2).toString());
                break;
        }
        return  rt;
    }

    //注册检查函数
    public boolean check_Register(String accountname,String password, String testnum,String truetestnum,Connection ct)
    {

        if(Search_Sqlserver(ct,accountname).size() == 0 && testnum.equals(truetestnum))
        {
            return true;
        }
        else
            return false;
    }


    //登录普通用户检查函数
    public boolean check_Login(String accountname,String password, String testnum,String truetestnum,Connection ct)
    {

        if(Search_Login(ct,accountname,password).size() != 0 && testnum.equals(truetestnum))
        {
            return true;
        }
        else
            return false;
    }

    //登录职员检查函数
    public boolean check_LoginStaff(String accountname,String password, String testnum,String truetestnum,Connection ct)
    {

        if(Search_LoginStaff(ct,accountname,password).size() != 0 && testnum.equals(truetestnum))
        {
            return true;
        }
        else
            return false;
    }

    //登录普通用户函数
    public void Login(Connection ct,Stage primarystage)
    {
        //信息输入框
        TextField accountname = new TextField();
        TextField password = new TextField();
        TextField testfield = new TextField();
        //标签框
        String testnum [] = Testnum();
        Label anlabel = new Label("账户名");
        Label pwlabel = new Label("密码");
        Label tflabel = new Label("验证码(均为整数)" + testnum[0]);
        tflabel.setText("验证码(均为整数)" + testnum[0]);
        anlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        pwlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        Label test = new Label(testnum[1]);
        //确认按钮
        Button bt = new Button("确认");
        bt.setOnAction(e->
        {
            if(check_Login(accountname.getText(),password.getText(),testfield.getText(),testnum[1],ct))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("登录成功");
                alert.setContentText("恭喜您登录成功!");
                alert.show();
                user_View(ct,primarystage,accountname.getText());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("登录信息有误");
                alert.setContentText("登录信息有误,请重新注册!");
                alert.show();
                tflabel.setText("验证码(均为整数)" + testnum[0]);
                test.setText(testnum[1]);
            }
        });

        Button returnbt = new Button("返回");
        returnbt.setOnAction(event ->
        {
            try
            {
                primarystage.setTitle("银行模拟系统");
                primarystage.setScene(mainmenu(primarystage));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        //Gridpane构架
        GridPane register_menu = new GridPane();
        register_menu.add(accountname,1,0);
        register_menu.add(password,1,2);
        register_menu.add(testfield,1,4);
        register_menu.add(anlabel,0,0);
        register_menu.add(pwlabel,0,2);
        register_menu.add(tflabel,0,4);
        // register_menu.add(test,0,5);
        register_menu.add(bt,1,7);
        register_menu.add(returnbt,2,7);
        register_menu.setAlignment(Pos.CENTER);
        //背景图片
        StackPane register_background = new StackPane(register_menu);
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        register_background.setBackground(bGround);//设置背景图片
        //新scene
        Scene register_scene = new Scene(register_background,700,700);
        primarystage.setScene(register_scene);
        primarystage.setTitle("用户登录界面");
        primarystage.show();
    }

    //检测职员的权限等级
    boolean staff_Or_Manager(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String quiry = new String("SELECT acc_rank FROM Admins WHERE acc_num = '" + username + "'");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1) == 0)
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    //登录职员用户函数
    public void login_Staff(Connection ct,Stage primarystage)
    {
        //信息输入框
        TextField accountname = new TextField();
        TextField password = new TextField();
        TextField testfield = new TextField();
        //标签框
        String testnum [] = Testnum();
        Label anlabel = new Label("账户名");
        Label pwlabel = new Label("密码");
        Label tflabel = new Label("验证码(均为整数)" + testnum[0]);
        tflabel.setText("验证码(均为整数)" + testnum[0]);
        anlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        pwlabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        tflabel.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        Label test = new Label(testnum[1]);
        //确认按钮
        Button bt = new Button("确认");
        bt.setOnAction(e->
        {
            if(check_LoginStaff(accountname.getText(),password.getText(),testfield.getText(),testnum[1],ct))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("登录成功");
                alert.setContentText("恭喜您登录成功!");
                alert.show();
                if(staff_Or_Manager(ct,accountname.getText()))
                    staff_View(ct,primarystage,accountname.getText());
                else
                    manager_View(ct,primarystage,accountname.getText());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("登录信息有误");
                alert.setContentText("登录信息有误,请重新注册!");
                alert.show();
                tflabel.setText("验证码(均为整数)" + testnum[0]);
                test.setText(testnum[1]);
            }
        });
        Button returnbt = new Button("返回");
        returnbt.setOnAction(event ->
        {
            try
            {
                primarystage.setTitle("银行模拟系统");
                primarystage.setScene(mainmenu(primarystage));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        //Gridpane构架
        GridPane register_menu = new GridPane();
        register_menu.add(accountname,1,0);
        register_menu.add(password,1,2);
        register_menu.add(testfield,1,4);
        register_menu.add(anlabel,0,0);
        register_menu.add(pwlabel,0,2);
        register_menu.add(tflabel,0,4);
        // register_menu.add(test,0,5);
        register_menu.add(bt,1,7);
        register_menu.add(returnbt,2,7);
        register_menu.setAlignment(Pos.CENTER);
        //背景图片
        StackPane register_background = new StackPane(register_menu);
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        register_background.setBackground(bGround);//设置背景图片
        //新scene
        Scene register_scene = new Scene(register_background,700,700);
        primarystage.setScene(register_scene);
        primarystage.setTitle("职员登录界面");
        primarystage.show();
    }

    //查询当前用户是否处于冻结状态
    boolean check_Userstatus(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String quiry = new String("SELECT acc_status FROM Account WHERE acc_num = '" + username + "'");
            ps = ct.prepareStatement(quiry);
            rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1) == 1)
                return true;
            else
                return false;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    //查询普通用户当前余额
    public String Search_Balance(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String  rt = new String();
        try {
            if(check_Userstatus(ct,username))
            {
                String quiry = new String("SELECT last_money FROM Account WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                rt = rs.getString(1);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("您的账户异常");
                alert.setContentText("您的账户被冻结 无法进行当前操作!");
                alert.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询普通用户姓名
    public String Search_Name(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String  rt = new String();
        try {
            if(check_Userstatus(ct,username))
            {
                String quiry = new String("SELECT name FROM Users WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                rt = rs.getString(1);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("您的账户异常");
                alert.setContentText("您的账户被冻结 无法进行当前操作!");
                alert.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询普通用户性别
    public String Search_Sex(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String  rt = new String();
        try {
            if(check_Userstatus(ct,username))
            {
                String quiry = new String("SELECT sex FROM Users WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                rt = rs.getString(1);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("您的账户异常");
                alert.setContentText("您的账户被冻结 无法进行当前操作!");
                alert.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询普通用户电话
    public String Search_Tele(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String  rt = new String();
        try {
            if(check_Userstatus(ct,username))
            {
                String quiry = new String("SELECT tele FROM Users WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                rt = rs.getString(1);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("您的账户异常");
                alert.setContentText("您的账户被冻结 无法进行当前操作!");
                alert.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //查询普通用户密码
    public String Search_Pass(Connection ct,String username)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String  rt = new String();
        try {
            if(check_Userstatus(ct,username))
            {
                String quiry = new String("SELECT acc_pass FROM Users WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                rt = rs.getString(1);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("您的账户异常");
                alert.setContentText("您的账户被冻结 无法进行当前操作!");
                alert.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rt;
    }

    //加入日记
    public void add_Logs(Connection ct,int id,String date,String sta_num,String tar_num,double money)
    {
        try {
            String update = new String("INSERT INTO Logs values('" + id + "' , '" + date + "' , '" + sta_num + "' , '" + tar_num + "' , '" + money + "')");
            PreparedStatement ps;
            ps = ct.prepareStatement(update);
            ps.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    //用户存款
    public void user_deposit(Connection ct,Stage primarystage,String username)
    {
        VBox depositVB = new VBox();
        GridPane depositGP = new GridPane();
        Label depositlb = new Label("存款金额: ");
        TextField deposittf = new TextField();
        Button depositbt = new Button("存款");
        depositGP.add(depositlb,0,0);
        depositGP.add(deposittf,1,0);
        depositGP.setAlignment(Pos.CENTER);
        depositVB.getChildren().add(depositGP);
        depositVB.getChildren().add(depositbt);
        depositVB.setAlignment(Pos.CENTER);
        depositVB.setSpacing(20);



        depositbt.setOnAction(e->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try
            {
                String quiry = new String("SELECT last_money FROM Account WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                if((new Double(deposittf.getText()).doubleValue()) > 20000)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("存款失败");
                    alert.setContentText("存款超过20000元限额 ,无法进行当前操作!");
                    alert.show();
                }
                else {
                    double sum = rs.getDouble(1) + new Double(deposittf.getText()).doubleValue();
                    String update = new String("update Account set last_money = " + sum + " where acc_num = '" + username + "'");
                    PreparedStatement ps1 = null;
                    ps1 = ct.prepareStatement(update);
                    ps1.executeUpdate();
//                    cur_balance.setText(Search_Balance(ct, username));

                    String quiry2 = new String("SELECT id FROM Users Where acc_num = '" + username + "'");
                    ps = ct.prepareStatement(quiry2);
                    rs = ps.executeQuery();
                    rs.next();
                    int id = rs.getInt(1);
                    Date date = new Date();
                    add_Logs(ct, id, date.toString(), username, username, new Double(deposittf.getText()).doubleValue());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("存款成功");
                    double balance_now = Double.parseDouble(Search_Balance(ct,username));
                    alert.setContentText("存款成功!当前余额" + balance_now + "元。");
                    alert.show();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        Scene depositScene = new Scene(depositVB,300,210);
        Stage depositStage = new Stage();
        depositStage.setTitle("存款");
        depositStage.setScene(depositScene);
        depositStage.show();
    }

    public void user_draw(Connection ct,Stage primarystage,String username)
    {
        VBox drawVB = new VBox();
        GridPane drawGP = new GridPane();
        Label drawlb = new Label("取款金额: ");
        TextField drawtf = new TextField();
        Button drawbt = new Button("取款");
        drawGP.add(drawlb,0,0);
        drawGP.add(drawtf,1,0);
        drawGP.setAlignment(Pos.CENTER);
        drawVB.getChildren().add(drawGP);
        drawVB.getChildren().add(drawbt);
        drawVB.setAlignment(Pos.CENTER);
        drawVB.setSpacing(20);

        drawbt.setOnAction(e->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try
            {
                String quiry = new String("SELECT last_money FROM Account WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                double sum = rs.getDouble(1) - new Double(drawtf.getText()).doubleValue();
                if(sum < 0)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("取款失败");
                    alert.setContentText("余额不足,无法进行当前操作!");
                    alert.show();
                }
                else
                {
                    String update = new String("update Account set last_money = " + sum + " where acc_num = '" + username + "'");
                    PreparedStatement ps1 = null;
                    ps1 = ct.prepareStatement(update);
                    ps1.executeUpdate();
//                    cur_balance.setText(Search_Balance(ct, username));

                    String quiry2 = new String("SELECT id FROM Users Where acc_num = '" + username + "'");
                    ps = ct.prepareStatement(quiry2);
                    rs = ps.executeQuery();
                    rs.next();
                    int id = rs.getInt(1);
                    Date date = new Date();
                    add_Logs(ct, id, date.toString(), username, username, 0 - new Double(drawtf.getText()).doubleValue());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("取款成功");
                    double balance_now = Double.parseDouble(Search_Balance(ct,username));
                    alert.setContentText("取款成功!当前余额" + balance_now + "元。");
                    alert.show();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        Scene drawScene = new Scene(drawVB,300,210);
        Stage drawStage = new Stage();
        drawStage.setTitle("取款");
        drawStage.setScene(drawScene);
        drawStage.show();
    }

    public void user_transfer(Connection ct,Stage primarystage,String username)
    {
        VBox transferVB = new VBox();
        GridPane transferGP = new GridPane();
        Label transferlb = new Label("转账金额: ");
        Label transferUserlb = new Label("目标用户: ");
        TextField transfertf = new TextField();
        TextField transferUsertf = new TextField();
        Button transferbt = new Button("转账");
        transferGP.add(transferUserlb,0,0);
        transferGP.add(transferUsertf,1,0);
        transferGP.add(transferlb,0,1);
        transferGP.add(transfertf,1,1);
        transferGP.setAlignment(Pos.CENTER);
        transferVB.getChildren().add(transferGP);
        transferVB.getChildren().add(transferbt);
        transferVB.setAlignment(Pos.CENTER);
        transferVB.setSpacing(20);



        transferbt.setOnAction(e->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try
            {
                String quiry = new String("SELECT last_money FROM Account WHERE acc_num = '" + username + "'");
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                rs.next();
                String quiry2 = new String("SELECT acc_num FROM Account WHERE acc_num = '" + transferUsertf.getText() + "'");
                PreparedStatement ps1 = ct.prepareStatement(quiry2);
                ResultSet rs2 = ps1.executeQuery();
                if(!rs2.next())
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("您要转账的目标用户不存在");
                    alert.setContentText("您要转账的目标用户不存在 无法进行当前操作!");
                    alert.show();
                }
                else if(rs.getDouble(1) < new Double(transfertf.getText()).doubleValue())
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("您的账户余额不足");
                    alert.setContentText("您的账户余额不足 无法进行当前操作!");
                    alert.show();
                }
                else
                {

                    double new_bal = (rs.getDouble(1) - new Double(transfertf.getText()).doubleValue());
                    String update = new String("UPDATE Account SET last_money = " + new_bal +  " where acc_num = '" + username + "'");
                    ps = ct.prepareStatement(update);
                    ps.executeUpdate();

                    String quiry3 = new String("SELECT last_money FROM Account WHERE acc_num = '" + transferUsertf.getText() + "'");
                    ps = ct.prepareStatement(quiry3);
                    rs = ps.executeQuery();
                    rs.next();
                    double tar_bal = rs.getDouble(1);

                    String update1 = new String("UPDATE Account SET last_money = " + (new Double(transfertf.getText()).doubleValue() + tar_bal) +  " where acc_num = '" + transferUsertf.getText() + "'");
                    ps = ct.prepareStatement(update1);
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("转账成功");
                    double balance_now = Double.parseDouble(Search_Balance(ct,username));
                    alert.setContentText("转账成功!当前余额" + balance_now + "元。");
                    alert.show();
//                    cur_balance.setText(Search_Balance(ct,username));

                    String quiry4 = new String("SELECT id FROM Users Where acc_num = '" + username + "'");
                    ps = ct.prepareStatement(quiry4);
                    rs = ps.executeQuery();
                    rs.next();
                    int id = rs.getInt(1);
                    Date date = new Date();
                    add_Logs(ct,id,date.toString(),username,transferUsertf.getText(),new Double(transfertf.getText()).doubleValue());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        Scene transferScene = new Scene(transferVB,300,210);
        Stage transferStage = new Stage();
        transferStage.setTitle("转账");
        transferStage.setScene(transferScene);
        transferStage.show();
    }

    //用户视图
    public void user_View(Connection ct,Stage primarystage,String username)
    {
        //布局
        VBox choose_bts_vbox = new VBox();
        //存款、取款、转账、关闭账户
        Button depositbt = new Button("存款");
        Button drawbt = new Button("取款");
        Button transferbt = new Button("转账");
        Button searchbt = new Button("查询交易记录");
        Button balancebt = new Button("查询余额");
        Button returnbt = new Button("退出登录");
        Button updatebt = new Button("更新个人信息");
        Label noticelb = new Label("欢迎用户" + username + ",请选择您需要进行的业务：");
        noticelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        searchbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        searchbt.setMinSize(150,50);
        balancebt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        balancebt.setMinSize(150,50);
        depositbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        drawbt.setMinSize(150,50);
        transferbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        depositbt.setMinSize(150,50);
        drawbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        transferbt.setMinSize(150,50);
        returnbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        returnbt.setMinSize(150,50);
        updatebt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        updatebt.setMinSize(150,50);

        choose_bts_vbox.getChildren().add(noticelb);
        choose_bts_vbox.getChildren().add(depositbt);
        choose_bts_vbox.getChildren().add(drawbt);
        choose_bts_vbox.getChildren().add(transferbt);
        choose_bts_vbox.getChildren().add(searchbt);
        choose_bts_vbox.getChildren().add(balancebt);
        choose_bts_vbox.getChildren().add(updatebt);
        choose_bts_vbox.getChildren().add(returnbt);
        choose_bts_vbox.setSpacing(10);
        choose_bts_vbox.setAlignment(Pos.CENTER);

        //button功能
        updatebt.setOnAction(e->
        {
            update_user(ct,primarystage,username);
        });

        //存款
        depositbt.setOnAction(e->
        {
            user_deposit(ct,primarystage,username);
        });

        //取款
        drawbt.setOnAction(e->
        {
            user_draw(ct,primarystage,username);
        });
        //转账
        transferbt.setOnAction(e->
        {
            user_transfer(ct,primarystage,username);
        });

        //查询余额
        balancebt.setOnAction(e->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("查询余额");
            double balance_now = Double.parseDouble(Search_Balance(ct,username));
            alert.setContentText("当前余额" + balance_now + "元。");
            alert.show();
        });

        //查询
        searchbt.setOnAction(e->
        {
            ScrollPane information = new ScrollPane();
            information.setMinHeight(200);
            information.setMaxHeight(200);
            PreparedStatement ps = null;
            ResultSet rs = null;
            String content = new String();
            try
            {
                String content1 = new String("ID ");
                String content2 = new String("时间 ");
                String content3 = new String("发起账户 ");
                String content4 = new String("目标账户 ");
                String content5 = new String("金额 \n");
                String quiry = new String("Select * from Logs where sta_num = '" + username + "'");
                content = content1 + content2 + content3 + content4 + content5;
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content += rs.getString(1) +" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) ;
                    content += "\n";
                }

                Label contentlb = new Label();

                contentlb.setText(content);
                information.setContent(contentlb);
                Scene searchScene = new Scene(information,300,210);
                Stage searchStage = new Stage();
                searchStage.setTitle("交易记录");
                searchStage.setScene(searchScene);
                searchStage.show();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        returnbt.setOnAction(event ->
        {
            try
            {
                primarystage.setScene(mainmenu(primarystage));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        choose_bts_vbox.setBackground(bGround);//设置背景图片
        Scene user_scene = new Scene(choose_bts_vbox,700,700);
        primarystage.setTitle("用户");
        primarystage.setScene(user_scene);
        primarystage.show();
    }

    //用户修改信息视图
    public  void update_user(Connection ct,Stage primarystage,String username)
    {
        //Label布局
        Label namelb = new Label("姓名: ");
        Label sexlb = new Label("性别: ");
        Label phonelb = new Label("电话: ");
        Label passwordlb = new Label("密码: ");
//        Label removelb = new Label("如欲销户 请联系职员处理");
        namelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        sexlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        passwordlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        phonelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
//        removelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");

        //Textfield布局
        TextField nametf = new TextField();
        TextField sextf = new TextField();
        TextField phonetf = new TextField();
        TextField passwordtf = new TextField();

        nametf.setText(Search_Name(ct,username));
        sextf.setText(Search_Sex(ct,username));
        phonetf.setText(Search_Tele(ct,username));
        passwordtf.setText(Search_Pass(ct,username));

        //Button
        Button savebt = new Button("修改");
        Button returnbt = new Button("返回");

        //Button功能
        savebt.setOnAction(event ->
        {
            try {
                PreparedStatement ps;
                String update = new String("UPDATE Users SET name =  '" + nametf.getText() + "'  where acc_num = '" + username + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                update = new String("UPDATE Users SET sex =  '" + sextf.getText() + "'  where acc_num = '" + username + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                update = new String("UPDATE Users SET tele =  '" + phonetf.getText() + "'  where acc_num = '" + username + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                update = new String("UPDATE Users SET acc_pass =  '" + passwordtf.getText() + "'  where acc_num = '" + username + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("修改成功");
                alert.setContentText("修改个人信息成功!");
                alert.show();

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        returnbt.setOnAction(event ->
        {
            user_View(ct, primarystage, username);
        });

        //gridpane布局
        GridPane update_pane = new GridPane();
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        update_pane.setBackground(bGround);//设置背景图片
        update_pane.setAlignment(Pos.CENTER);
        update_pane.add(namelb,0,0);
        update_pane.add(nametf,1,0);
        update_pane.add(sexlb,0,2);
        update_pane.add(sextf,1,2);
        update_pane.add(phonelb,0,4);
        update_pane.add(phonetf,1,4);
        update_pane.add(passwordlb,0,6);
        update_pane.add(passwordtf,1,6);
        update_pane.add(savebt,1,7);
        update_pane.add(returnbt,2,7);

        //scene布局
        Scene update_scene = new Scene(update_pane,700,700);
        primarystage.setTitle("用户信息修改");
        primarystage.setScene(update_scene);
        primarystage.show();
    }

    //职员视图
    public void staff_View(Connection ct,Stage primarystage,String username)
    {
        //Label 布局
        Label accsearchlb = new Label("输入用户名: ");
        Label logsearchlb = new Label("输入用户名: ");
        Label frozelb = new Label("冻结用户: ");
        Label unfrozelb = new Label("解冻用户: ");
        Label removelb = new Label("输入用户名: ");
        accsearchlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        logsearchlb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        removelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        frozelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        unfrozelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");

        //滚动框
        ScrollPane accinformation = new ScrollPane();
        ScrollPane loginformation = new ScrollPane();

        //Textfield
        TextField accsearchtf = new TextField();
        TextField logsearchtf = new TextField();
        TextField frozetf = new TextField();
        TextField unfrozetf = new TextField();
        TextField removetf = new TextField();

        //Button 布局
        Button returnbt = new Button("退出登录");
        Button accsearchbt = new Button("查找账户信息");
        Button logsearchbt = new Button("查找交易信息");
        Button frozebt = new Button("冻结用户");
        Button unfrozebt = new Button("解冻用户");
        Button removebt = new Button("销户");

        // Button 布局2
        Button accsearchbt2 = new Button("确定");
        Button logsearchbt2 = new Button("确定");
        Button frozebt2 = new Button("确定");
        Button unfrozebt2 = new Button("确定");
        Button removebt2 = new Button("确定");


        //Button 功能
        returnbt.setOnAction(event ->
        {
            primarystage.setTitle("银行模拟系统");
            primarystage.setScene(mainmenu(primarystage));
        });

        accsearchbt.setOnAction(event ->
        {
            accsearchtf.clear();
            Label contentlb = new Label();
            accinformation.setContent(contentlb);
            accinformation.setMinHeight(200);
            accinformation.setMaxHeight(200);

            VBox drawVB = new VBox();
            GridPane drawGP = new GridPane();

            drawGP.add(accsearchlb,0,0);
            drawGP.add(accsearchtf,1,0);
            drawGP.add(accsearchbt2,1,0);
            drawGP.add(accinformation,1,0);
            drawGP.setAlignment(Pos.CENTER);
            drawVB.getChildren().add(drawGP);
            drawVB.getChildren().add(accsearchbt2);
            drawVB.getChildren().add(accinformation);
            drawVB.setAlignment(Pos.CENTER);
            drawVB.setSpacing(20);

            Scene accsearchScene = new Scene(drawVB, 400, 400);
            Stage accsearchStage = new Stage();
            accsearchStage.setTitle("按用户名查找账户信息");
            accsearchStage.setScene(accsearchScene);
            accsearchStage.show();
        });

        accsearchbt2.setOnAction(event ->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String content = new String();
            try
            {
                String content1 = new String("账号 ");
                String content2 = new String("姓名 ");
                String content3 = new String("操作时间 ");
                String content4 = new String("余额 ");
                String content5 = new String("状态 \n");
                String quiry = new String("Select acc_num,acc_name,optime,last_money,acc_status from Account where acc_num = '" + accsearchtf.getText() + "'");
                content = content1 + content2 + content3 + content4 + content5;
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content += rs.getString(1) +" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) ;
                    content += "\n";
                }

                Label contentlb = new Label();

                contentlb.setText(content);
                accinformation.setContent(contentlb);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        removebt.setOnAction(event ->
        {
            removetf.clear();

            VBox drawVB = new VBox();
            GridPane drawGP = new GridPane();
            drawGP.add(removelb,0,0);
            drawGP.add(removetf,1,0);
            drawGP.setAlignment(Pos.CENTER);
            drawVB.getChildren().add(drawGP);
            drawVB.getChildren().add(removebt2);
            drawVB.setAlignment(Pos.CENTER);
            drawVB.setSpacing(20);

            Scene removeScene = new Scene(drawVB, 450, 250);
            Stage removeStage = new Stage();
            removeStage.setTitle("销户");
            removeStage.setScene(removeScene);
            removeStage.show();


        });

        removebt2.setOnAction(event ->
        {
            PreparedStatement ps;
            try
            {
                String remove = new String("DELETE FROM Account WHERE acc_num = '" + removetf.getText() + "'");
                ps = ct.prepareStatement(remove);
                ps.executeUpdate();
                String remove1 = new String("DELETE FROM Logs WHERE sta_num = '" + removetf.getText() + "'");
                ps = ct.prepareStatement(remove1);
                ps.executeUpdate();
                String remove2 = new String("DELETE FROM Users WHERE acc_num = '" + removetf.getText() + "'");
                ps = ct.prepareStatement(remove2);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("销户成功");
                alert.setContentText("销户成功!");
                alert.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });


        logsearchbt.setOnAction(event ->
        {
            logsearchtf.clear();
            Label contentlb = new Label();
            loginformation.setContent(contentlb);
            loginformation.setMinHeight(200);
            loginformation.setMaxHeight(200);

            VBox drawVB = new VBox();
            GridPane drawGP = new GridPane();

            drawGP.add(logsearchlb,0,0);
            drawGP.add(logsearchtf,1,0);
            drawGP.add(logsearchbt2,1,0);
            drawGP.add(loginformation,1,0);
            drawGP.setAlignment(Pos.CENTER);
            drawVB.getChildren().add(drawGP);
            drawVB.getChildren().add(logsearchbt2);
            drawVB.getChildren().add(loginformation);
            drawVB.setAlignment(Pos.CENTER);
            drawVB.setSpacing(20);

            Scene logsearchScene = new Scene(drawVB, 400, 400);
            Stage logsearchStage = new Stage();
            logsearchStage.setTitle("按用户名查找交易信息");
            logsearchStage.setScene(logsearchScene);
            logsearchStage.show();


        });

        logsearchbt2.setOnAction(event ->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String content = new String();
            try
            {
                String content1 = new String("ID ");
                String content2 = new String("日期 ");
                String content3 = new String("发起账户 ");
                String content4 = new String("目标账户 ");
                String content5 = new String("金额 \n");
                String quiry = new String("Select * from Logs where sta_num = '" + logsearchtf.getText() + "'");
                content = content1 + content2 + content3 + content4 + content5;
                ps = ct.prepareStatement(quiry);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content += rs.getString(1) +" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) ;
                    content += "\n";
                }

                Label contentlb = new Label();

                contentlb.setText(content);
                loginformation.setContent(contentlb);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        frozebt.setOnAction(event ->
        {
            frozetf.clear();

            VBox drawVB = new VBox();
            GridPane drawGP = new GridPane();
            drawGP.add(frozelb,0,0);
            drawGP.add(frozetf,1,0);
            drawGP.setAlignment(Pos.CENTER);
            drawVB.getChildren().add(drawGP);
            drawVB.getChildren().add(frozebt2);
            drawVB.setAlignment(Pos.CENTER);
            drawVB.setSpacing(20);

            Scene frozeScene = new Scene(drawVB, 450, 250);
            Stage frozeStage = new Stage();
            frozeStage.setTitle("冻结用户");
            frozeStage.setScene(frozeScene);
            frozeStage.show();
        });

        frozebt2.setOnAction(event ->
        {
            PreparedStatement ps;
            try{
                String update = new String("UPDATE Account SET acc_status =  0  where acc_num = '" + frozetf.getText() + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("冻结成功");
                alert.setContentText("冻结用户成功!");
                alert.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        unfrozebt.setOnAction(event ->
        {
            unfrozetf.clear();

            VBox drawVB = new VBox();
            GridPane drawGP = new GridPane();
            drawGP.add(unfrozelb,0,0);
            drawGP.add(unfrozetf,1,0);
            drawGP.setAlignment(Pos.CENTER);
            drawVB.getChildren().add(drawGP);
            drawVB.getChildren().add(unfrozebt2);
            drawVB.setAlignment(Pos.CENTER);
            drawVB.setSpacing(20);

            Scene unfrozeScene = new Scene(drawVB, 450, 250);
            Stage unfrozeStage = new Stage();
            unfrozeStage.setTitle("解冻用户");
            unfrozeStage.setScene(unfrozeScene);
            unfrozeStage.show();
        });

        unfrozebt2.setOnAction(event ->
        {
            PreparedStatement ps;
            try{
                String update = new String("UPDATE Account SET acc_status =  1  where acc_num = '" + unfrozetf.getText() + "'");
                ps = ct.prepareStatement(update);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("解冻成功");
                alert.setContentText("解冻用户成功!");
                alert.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        //gridpane 布局
        GridPane staff_pane = new GridPane();
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
//        staff_pane.setBackground(bGround);//设置背景图片
//        staff_pane.setAlignment(Pos.CENTER);
//        staff_pane.add(accsearchlb,0,0);
//        staff_pane.add(accsearchtf,1,0);
//        staff_pane.add(accsearchbt,2,0);
//        staff_pane.add(accinformation,1,2);
//        staff_pane.add(logsearchlb,0,4);
//        staff_pane.add(logsearchtf,1,4);
//        staff_pane.add(logsearchbt,2,4);
//        staff_pane.add(loginformation,1,5);
//        staff_pane.add(frozelb,0,8);
//        staff_pane.add(frozetf,1,8);
//        staff_pane.add(frozebt,2,8);
//        staff_pane.add(unfrozelb,0,10);
//        staff_pane.add(unfrozetf,1,10);
//        staff_pane.add(unfrozebt,2,10);
//        staff_pane.add(removelb,0,12);
//        staff_pane.add(removetf,1,12);
//        staff_pane.add(removebt,2,12);
//        staff_pane.add(returnbt,2,13);

        //布局
        VBox choose_bts_vbox = new VBox();

        Label noticelb = new Label("欢迎用户" + username + ",请选择您需要进行的业务：");
        noticelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        accsearchbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        accsearchbt.setMinSize(150,50);
        logsearchbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        logsearchbt.setMinSize(150,50);
        frozebt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        frozebt.setMinSize(150,50);
        unfrozebt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        unfrozebt.setMinSize(150,50);
        removebt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        removebt.setMinSize(150,50);
        returnbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        returnbt.setMinSize(150,50);

        choose_bts_vbox.getChildren().add(noticelb);
        choose_bts_vbox.getChildren().add(accsearchbt);
        choose_bts_vbox.getChildren().add(logsearchbt);
        choose_bts_vbox.getChildren().add(frozebt);
        choose_bts_vbox.getChildren().add(unfrozebt);
        choose_bts_vbox.getChildren().add(removebt);
        choose_bts_vbox.getChildren().add(returnbt);
        choose_bts_vbox.setSpacing(10);
        choose_bts_vbox.setAlignment(Pos.CENTER);
        choose_bts_vbox.setBackground(bGround);
        //scene布局
        Scene staff_scene = new Scene(choose_bts_vbox,700,700);
        primarystage.setTitle("职员");
        primarystage.setScene(staff_scene);
        primarystage.show();
    }

    //经理视图
    public void manager_View(Connection ct,Stage primarystage,String username)
    {
        //button布局
        Button inforbt = new Button("查询员工信息");
        Button staff_registerbt = new Button("注册员工");
        inforbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        staff_registerbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");

        //滚动框
        ScrollPane information = new ScrollPane();

        //button 功能
        inforbt.setOnAction(e->
        {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String content = new String();
            try
            {
                String content1 = new String("账户信息\n");
                String content2 = new String("管理员信息\n");
                String content3 = new String("日记信息\n");
                String content4 = new String("用户信息\n");
                String quiry1 = new String("Select * from Account ");
                String quiry2 = new String("Select * from Admins");
                String quiry3 = new String("Select * from Logs");
                String quiry4 = new String("Select * from Users");

                ps = ct.prepareStatement(quiry1);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content1 += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) + " " + rs.getString(6)  + " " + rs.getString(7);
                    content1 += "\n";
                }

                ps = ct.prepareStatement(quiry2);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content2 += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) + " " + rs.getString(6)  + " " + rs.getString(7);
                    content2 += "\n";
                }

                ps = ct.prepareStatement(quiry3);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content3 += rs.getString(1) +" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) ;
                    content3 += "\n";
                }

                ps = ct.prepareStatement(quiry4);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    content4 += rs.getString(1) +" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString (4) + " " + rs.getString(5) + " " + rs.getString(6) ;
                    content4 += "\n";
                }

                content = content1 + content2 + content3 + content4;
                Label contentlb = new Label();
                contentlb.setText(content);
                information.setContent(contentlb);

                GridPane informaPane = new GridPane();
                informaPane.add(information,0,0);
                Scene informaScene = new Scene(informaPane, 350, 250);
                Stage informaStage = new Stage();
                informaStage.setTitle("查看员工信息");
                informaStage.setScene(informaScene);
                informaStage.show();

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        staff_registerbt.setOnAction(e->
        {
            Register_Staff(ct,primarystage,username);
        });

        Button returnbt = new Button("退出登录");
        returnbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        returnbt.setOnAction(event ->
        {
            try
            {
                primarystage.setTitle("银行模拟系统");
                primarystage.setScene(mainmenu(primarystage));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        //gridpane 布局
        GridPane manager_pane = new GridPane();
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);

        VBox choose_bts_vbox = new VBox();

        Label noticelb = new Label("欢迎用户" + username + ",请选择您需要进行的业务：");
        noticelb.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 30px");
        inforbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        inforbt.setMinSize(150,50);
        staff_registerbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        staff_registerbt.setMinSize(150,50);
        returnbt.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        returnbt.setMinSize(150,50);

        choose_bts_vbox.getChildren().add(noticelb);
        choose_bts_vbox.getChildren().add(inforbt);
        choose_bts_vbox.getChildren().add(staff_registerbt);
        choose_bts_vbox.getChildren().add(returnbt);

        choose_bts_vbox.setSpacing(10);
        choose_bts_vbox.setAlignment(Pos.CENTER);
        choose_bts_vbox.setBackground(bGround);
        //scene布局
        Scene manager_scene = new Scene(choose_bts_vbox,700,700);
        primarystage.setTitle("经理");
        primarystage.setScene(manager_scene);
        primarystage.show();

    }


    //主菜单函数
    public Scene mainmenu(Stage primarystage)
    {
        //连接数据库
        Connection ct = Connect_Sqlserver();
        //布局容器
        StackPane main_menu = new StackPane();
        VBox tmp_stPane_buttons = new VBox();
        //背景图片
        Image background_image = new Image("https://img.tukuppt.com/bg_grid/00/05/72/1rGD3Y0Wsx.jpg!/fh/350");
        BackgroundImage bg_i = new BackgroundImage(background_image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,700,true,true,true,true));
        Background bGround = new Background(bg_i);
        main_menu.setBackground(bGround);//设置背景图片
        //label标签
        Label welcome_words = new Label("银行管理系统欢迎您！");
        welcome_words.setStyle("-fx-font-family: '仿宋'; -fx-font-size: 40px");//使用css样式进行字体确认
        //设置按钮
        Button register = new Button("注册");
        Button login = new Button("用户登录");
        Button login_staff = new Button("职员登录");
        register.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        register.setMinSize(120,50);
        login.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        login.setMinSize(120,50);
        login_staff.setStyle("-fx-font-family: '微软雅黑'; -fx-font-size: 20px");
        login_staff.setMinSize(120,50);

        tmp_stPane_buttons.setSpacing(10);
        tmp_stPane_buttons.getChildren().add(welcome_words);
        tmp_stPane_buttons.getChildren().add(register);
        tmp_stPane_buttons.getChildren().add(login);
        tmp_stPane_buttons.getChildren().add(login_staff);
        //lamada函数实现注册功能
        register.setOnAction(e->
        {
            Register(ct,primarystage);
        });
        //lamada函数实现登录功能
        login.setOnAction(e->
        {
            Login(ct,primarystage);
        });
        login_staff.setOnAction(e->
                {
                    login_Staff(ct,primarystage);
                }
        );
        //对VBOX以及gridpane布局
        main_menu.getChildren().add(tmp_stPane_buttons);
        tmp_stPane_buttons.setAlignment(Pos.CENTER);
        main_menu.setAlignment(Pos.CENTER);
        Scene scene = new Scene(main_menu,700,700);
        return scene;
    }

    public static void main(String [] args)
    {
        Application.launch();
    }
}
