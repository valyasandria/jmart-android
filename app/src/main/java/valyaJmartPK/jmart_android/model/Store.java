package valyaJmartPK.jmart_android.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Store {

    public String REGEX_PHONE = "^(/d{9,12})$";
    public String REGEX_NAME = "^(?=^[A-Z])(?![A-Z a-z]{20,})((?=[A-Z a-z]{4,}).)((?!\\s{2}).)*$";
    public String name;
    public String address;
    public String phoneNumber;

    public Store(String name, String address, String phoneNumber)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
