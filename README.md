# testEncryptDecrypt
Here used BounCycastleAPI

Использована установка провайдера API.

1. Загрузить библиотеку bcprov-jdk16-146.jar (http://www.bouncycastle.org/latest_releases.html). 
2. Скопировать  .jar в каталог:
C:\Program Files\Java\jdk1.6.0_16\jre\lib\ext 

3. В файле  java.security (каталог \lib\security\  JRE добавить строку: 
security.provider.N=org.bouncycastle.jce.provider.BouncyCastleProvider 
(N – номер в последовательности) в блок 
# # List of providers and their preference orders (see above): # security.provider.1=sun.security.provider.Sun 
security.provider.2=sun.security.rsa.SunRsaSign 
security.provider.3=com.sun.net.ssl.internal.ssl.Provider 
security.provider.4=com.sun.crypto.provider.SunJCE 
security.provider.5=sun.security.jgss.SunProvider 
security.provider.6=com.sun.security.sasl.Provider 
security.provider.7=org.jcp.xml.dsig.internal.dom.XMLDSigRI 
security.provider.8=sun.security.smartcardio.SunPCSC 
security.provider.9=sun.security.mscapi.SunMSCAPI 

Например: 
security.provider.10=org.bouncycastle.jce.provider.BouncyCastleProvider 
 
4. Протестировать работу провайдера можно следующим кодом:  
import java.security.Security;   

public class Main {     
	public static void main(String[] args) {               
		if (Security.getProvider("BC") == null){             
			System.out.println("Bouncy Castle provider is NOT available");         
		}
		else
		{
			System.out.println("Bouncy Castle provider is available");         
		}     
	} 
} 

Положительный результат выведет 
Bouncy Castle provider is available 