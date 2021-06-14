import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class test {
    private static int a;
    private static String stuff(){
        try{
            throw new Exception();
        }catch (Exception e) {
            return "Exception thrown";
        }finally {
            a++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("toko\tmagari\tkaci");
    }
}
