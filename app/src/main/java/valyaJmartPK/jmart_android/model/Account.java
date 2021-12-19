package valyaJmartPK.jmart_android.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create account and validate their email & password
 * @author Valya Sandria Akiela
 */
public class Account extends Serializable {
    public static String REGEX_EMAIL = "^\\w+([\\.&`~-]?\\w+)*@\\w+([\\.-]?\\w+)+$";
    public static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d][^-\\s]{8,}$";

    public String name;
    public String email;
    public String password;
    public double balance;
    public Store store;

    /**
     * Used to store registered account
     * @param name user's name
     * @param email user's email
     * @param password user's password
     * @param balance user's balance
     * @param store user's store
     */
    public Account(String name, String email, String password, double balance, Store store)
    {

        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.store = store;

    }

}
