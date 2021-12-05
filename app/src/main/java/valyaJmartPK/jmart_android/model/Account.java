package valyaJmartPK.jmart_android.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable {
    public static String REGEX_EMAIL = "^\\w+([\\.&`~-]?\\w+)*@\\w+([\\.-]?\\w+)+$";
    public static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d][^-\\s]{8,}$";

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

}
