//Grace Cappella, 3/20/23, CIS-376-001
package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*The purpose of EmailTest is to cover at least 70% each of the 10 methods assigned 
 * in meaningful test cases. 
 * Preconditions: EmailConcrete must be created to utilize the abstract Email class.
 */
public class EmailTest {
	private EmailConcrete email;
	private static final String[] TEST_EMAILS = {"ab@c.com", "a.b@c.org", 
			"hdsfatjndskjtndksj@dsnatkjdsntkjsd.com.bd"};
	private static final String[] TEST_NULL_EMAILS = {};
	
	//Creates an instance of the email concrete class to test in each test method
	@Before
	public void setUpEmailTest() throws Exception{
		email = new EmailConcrete();	
	}
	
	//Tests a successful case of adding emails to the Bcc list.
	//Success: Assert the number of BccAddresses matches
	@Test
	public void testAddBcc() throws Exception{
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	//Tests a failure case of adding null emails to the Bcc list.
	//Success: Expected Exception thrown
	@Test(expected=EmailException.class)
	public void testAddBccException() throws Exception{
		email.addBcc(TEST_NULL_EMAILS);
	}

	//Tests a successful case of adding an email to the Cc list.
	//Success: Assert the number of CC addresses matches
	@Test
	public void testAddCc() throws Exception{
		email.addCc("abc@us.com");
		assertEquals(1, email.getCcAddresses().size());
	}
	
	//Tests a successful case of adding a header to the email.
	//Success: Assert the number of headers matches
	@Test
	public void testAddHeader() throws Exception{
		email.addHeader("NameOfHeader", "ValueOfHeader");
		assertEquals(1, email.getHeaders().size());
	}
	
	//Tests a failure case of adding a header to the email (invalid name)
	//Success: Expected Exception thrown
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderExceptionName() throws Exception{
		email.addHeader(null, "ValueOfHeader");
	}
	
	//Tests a failure case of adding a header to the email (invalid value)
	//Success: Expected Exception thrown
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderExceptionValue() throws Exception{
		email.addHeader("NameOfHeader", null);
	}
	
	//Tests a successful case of adding an email to the ReplyTo list
	//Success: Assert the number of ReplyTo addresses matches
	@Test
	public void testAddReplyTo() throws Exception{
		email.addReplyTo("emailaddress@gmail.com", "Name");
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	//Tests a successful case of building a MimeMessage
	//Success: Assert the subject of the MimeMessage is the same as the added subject 
	//(This indicates the mimemessage was built successfully)
	@Test
	public void testBuildMimeMessage() throws Exception{
		Properties props = new Properties();
		Session sesh = Session.getInstance(props);
		Object anObj = new Object();
		
		email.setMailSession(sesh);
		email.setSubject("This is the subject");
		email.addCc("cc@gm.c");
		email.addBcc("bcc@gm.c");
		email.setFrom("grace@washere.c");
		email.addTo("abc@g.c");
		email.addHeader("Name", "Value");
		email.addReplyTo("wendy@c.d");
		email.setContent(anObj, "String!");
		email.buildMimeMessage();
		MimeMessage msg = email.getMimeMessage();
		assertEquals(msg.getSubject(), "This is the subject");
	}
	
	//Tests a failure case of buildMimeMessage (Exception thrown on
	//invalid session properties when attempting to add POP3 as a protocol)
	//Success: Expected Exception Thrown
	@Test(expected=EmailException.class)
	public void testBuildMimeMessageEmailException() throws Exception{
		Properties props = new Properties();
		Session sesh = Session.getInstance(props);
		Object anObj = new Object();
		
		email.setMailSession(sesh);
		email.setSubject("This is the subject");
		email.addCc("cc@gm.c");
		email.addBcc("bcc@gm.c");
		email.setFrom("grace@washere.c");
		email.addTo("abc@g.c");
		email.addHeader("Name", "Value");
		email.addReplyTo("wendy@c.d");
		email.setContent(anObj, "String!");
		email.setPopBeforeSmtp(true, "newPopHost", "newPopUsername", "newPopPassword");
		email.buildMimeMessage();
	}
	
	//Tests a failure case of buildMimeMessage
	//Exception is thrown when trying to build a second MimeMessage
	//Success: Expected Exception thrown
	@Test(expected=IllegalStateException.class)
	public void testBuildMimeMessagesException() throws Exception{
		testBuildMimeMessage();
		email.buildMimeMessage();
	}
	
	//Tests a successful case of getHostName with a given session.
	//Success: Assert the hostName is null when given a null session
	@Test
	public void testGetHostNameSession() throws Exception{
		Properties props = new Properties();
		Session sesh = Session.getInstance(props);
		email.setMailSession(sesh);
		assertEquals(null, email.getHostName());
	}
	
	//Tests a successful case of getHostName without a session
	//Success: Assert the set hostName equals the getHostName
	@Test
	public void testGetHostNameNoSession() throws Exception{
		email.setHostName("numbas");
		assertEquals("numbas", email.getHostName());
	}
	
	//Tests a successful case of getMailSession
	//Success: Assert the smtp port of the session equals the smtp port of the email
	@Test
	public void testGetMailSession() throws Exception{
		email.setSmtpPort(1234);
		email.setSslSmtpPort("hello");
		email.setHostName("host");
		email.setSSLOnConnect(true);
		Session sesh = email.getMailSession();
		assertEquals(sesh.getProperty("mail.smtp.port"), email.getSmtpPort());
	}
	
	//Tests a successful case of getSentDate
	//Success: Assert the date equals the getSentDate
	@Test
	public void testGetSentDate() throws Exception{
		Date date = new Date();
		date.setTime(100000);
		email.setSentDate(date);
		assertEquals(date, email.getSentDate());
	}
	
	//Tests a successful case of getSentDate where the date is null
	//Success: Assert the date equals the getSentDate (which is null)
	@Test
	public void testGetSentDateNull() throws Exception{
		Date date = new Date();
		assertEquals(date, email.getSentDate());
	}
	
	//Tests a successful case of getSocketConnectionTimeout
	//Success: Assert the timeout equals the getSocketConnectionTimeout
	@Test
	public void testGetSocketConnectionTimeout() throws Exception{
		email.setSocketConnectionTimeout(100);
		assertEquals(100, email.getSocketConnectionTimeout());
	}
	
	//Tests a successful case of setFrom
	//Success: Assert the fromAddress equals the address given
	@Test
	public void testSetFrom() throws Exception{
		email.setFrom("myemail@gmail.com");
		InternetAddress address = new InternetAddress("myemail@gmail.com");
		assertEquals(email.getFromAddress(), address);
	}
	
	//Blank teardown
	@After
	public void teardownEmailTest() throws Exception{
		
	}
}
