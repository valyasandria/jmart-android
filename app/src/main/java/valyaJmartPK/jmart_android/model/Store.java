package valyaJmartPK.jmart_android.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Store {

    public String address;
    public String name;
    public String phoneNumber;

    public Store(String name, String address, String phoneNumber)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String toString()
    {
        return ("Name: " + this.name + "\naddress: " + this.address + "\nphoneNumber: " + this.phoneNumber);
    }

    public boolean validate(){
        Pattern pattern = Pattern.compile("^(/d{9,12})$");
        Matcher matcher = pattern.matcher(this.phoneNumber);
        Pattern pattern2 = Pattern.compile("^(?=^[A-Z])(?![A-Z a-z]{20,})((?=[A-Z a-z]{4,}).)((?!\\s{2}).)*$");
        Matcher matcher2 = pattern2.matcher(this.name);
        return matcher.find() && matcher2.find();
    }
}
