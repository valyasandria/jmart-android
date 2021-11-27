package valyaJmartPK.jmart_android.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Account extends Serializable {
    public static String REGEX_EMAIL = "^[A-Z0-9.&_*~]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static String REGEX_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    public String name;
    public String email;
    public String password;
    public double balance;
    public Store store;

    public Account(String name, String email, String password, double balance)
    {

        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;

    }

    public boolean validate(String email, String password)
    {
        //validasi email
        Pattern emailPattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = emailPattern.matcher(email);

        //validasi password
        Pattern passPattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matchPass = passPattern.matcher(password);

        return matcher.find() && matchPass.find();
    }
}
