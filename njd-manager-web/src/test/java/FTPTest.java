import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;


public class FTPTest {
	
	@Test
	public void testFtpClient() throws Exception {
		//创建一个ftpClient对象
		FTPClient client = new FTPClient();
		//创建ftp连接.默认是21
		client.connect("192.168.41.128", 21);
		//登录ftp服务器,使用用户名和密码；
		client.login("ftpuser", "woaiwojia1991");
		//上传文件
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\lyc.jpg"));
		//更改文件的上传路径
		client.changeWorkingDirectory("/home/ftpuser/www/images");
		//设置文件的上传格式
		client.setFileType(FTP.BINARY_FILE_TYPE);
		//最后一步上传文件
		client.storeFile("hello1.png", fileInputStream);
		//关闭连接
		client.logout();
		
	}
}
