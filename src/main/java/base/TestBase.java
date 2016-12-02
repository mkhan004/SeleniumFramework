package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import pages.MenuBar;
import pages.SignInPage;

@Listeners(listener.EliteListener.class)
public class TestBase extends Driver {
	
	String browserType = getProperty("browser");
	String appUrl = getProperty("appUrl");
	
	// object for all classes in `pages` package
	protected static SignInPage signInPage;
	protected static MenuBar menuBar;
	
	@BeforeSuite
	public void setUp() {
		try {
			if(getProperty("saucelabs") != null ) {
				if(Boolean.valueOf(getProperty("saucelabs"))) {
					setSauceLabs(appUrl);
				} else {
					setDriver(browserType, appUrl);
				}
			} else {
				setDriver(browserType, appUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		signInPage = PageFactory.initElements(driver, SignInPage.class);
		menuBar = PageFactory.initElements(driver, MenuBar.class);
	}
	
	@AfterSuite
	public void tearDown() {
		sendMail("banglaoutfitters@gmail.com", "shakilkhanny@gmail.com", "smtp.gmail.com", "something");
		driver.quit();
	}
	
	public static void sendMail(String emailTo, String emailFrom, String emailHost, String reportFile)
    {
        String to = emailTo;
        // Sender's email ID needs to be mentioned
        String from = emailFrom;
        // Assuming you are sending email from localhost
        String host = emailHost;
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.password", "As365827@");
        properties.setProperty("mail.smtp.port", "587");
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            // Set Subject: header field
            message.setSubject("Automation Test Report");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message

//            File indexHtml = new File(reportFile);
//
//            StringBuilder contentBuilder = new StringBuilder();
//            try {
//                BufferedReader in = new BufferedReader(new FileReader(indexHtml));
//                String str;
//                while ((str = in.readLine()) != null) {
//                    contentBuilder.append(str);
//                }
//                in.close();
//            } catch (IOException e) {
//            }
//            String htmlContent = contentBuilder.toString();
//
////            messageBodyPart.setContent("Please find the reports attached. \n\n Summary of Test \n Tests Passed : "+ TestRunnerUtilities.passTc + "/"+ TestRunnerUtilities.totalTc  + htmlContent + "\\n\\n Regards\\n Atom API Team", "text/html; charset=utf-8");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.dir") + "/Results.zip";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Results.zip");
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
            System.out.println("Email Send done.");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }

}
