import java.io.FileNotFoundException;
import java.io.IOException;

import com.texasjake95.commons.util.system.OS;

public class CommonsTest {
	
	public static void main(String args[]) throws FileNotFoundException, IOException
	{
		System.out.println(OS.getOS().name);
		System.out.println(System.getProperty("os.arch").toLowerCase());
	}
}