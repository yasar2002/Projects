import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		String salt="qwertyuiopoigfdsacvbnm,237890!@#$%^&*QWERTYUIKJHGFDSCVHJITRESXCVBL:";
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] messageDigest = md.digest((password+salt).getBytes());
	    StringBuilder builder=new StringBuilder(new BigInteger(1, messageDigest).toString(16));
	    return builder.reverse().toString();
	}
}
