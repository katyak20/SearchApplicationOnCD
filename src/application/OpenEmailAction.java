package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import explorer.Util;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.*;
/**
 * Trigger the default email client to open a window for composing a
 * new email. Prepopulate some of the fields such as recipient,
 * subject and body.
 */
public class OpenEmailAction extends Action
{
   private String recipient;
   private String subject;
   private String body;
   MyApplicationWindow window;

   public OpenEmailAction(MyApplicationWindow w) {
	   window = w;
	    setText("Email");
	    setToolTipText("Email AVTEC");
	    
      setRecipient("info@avtecsupport.com");
      setSubject("Question");
      setBody("Dear Mr Giddens ");
   
   setImageDescriptor(
   ImageDescriptor.createFromURL(newURL("file:icons/Mail_32x32-1.png")));
}
public static URL newURL(String url_name)
{
  try
  {
    return new URL(url_name);
  }
  catch (MalformedURLException e)
  {
    throw new RuntimeException("Malformed URL " + url_name, e);
  }
}

   public void setRecipient(String recipient) {
      this.recipient = recipient;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public void run() {

      // Determine the platform specific template

      String template;
      if (SWT.getPlatform().equals("win32")) {
         template = "mailto:${recipient}" + "?Subject=${subject}&Body=${body}";
      }
      else {
         // Put code here to test for various Linux email clients
         template = "mailto:${recipient}" + "?Subject=${subject}&Body=${body}";
      }

      // Construct a mailSpec based upon the template

      String mailSpec = buildMailSpec(template);

      // Use the mailSpec to launch the email client

      if (mailSpec.startsWith("mailto:")) {
         Program.launch(mailSpec);
      }
      else {
         try {
            Runtime.getRuntime().exec(mailSpec);
         }
         catch (IOException e) {
            System.out.println("Failed to open mail client: " + mailSpec + e);
         }
      }
     
   }

   private String buildMailSpec(String template) {
      StringBuffer buf = new StringBuffer(1000);
      int start = 0;
      while (true) {
         int end = template.indexOf("${", start);
         if (end == -1) {
            buf.append(template.substring(start));
            break;
         }
         buf.append(template.substring(start, end));
         start = template.indexOf("}", end + 2);
         if (start == -1) {
            buf.append(template.substring(end));
            break;
         }
         String key = template.substring(end + 2, start);
         if (key.equalsIgnoreCase("recipient")) {
            buf.append(recipient);
         }
         else if (key.equalsIgnoreCase("subject")) {
            buf.append(subject);
         }
         else if (key.equalsIgnoreCase("body")) {
            appendBody(buf);
         }
         start++;
      }
      return buf.toString();
   }

   private void appendBody(StringBuffer buf) {
      if (body == null)
         return;
      int start = 0;
      while (true) {
         int end = body.indexOf('\n', start);
         if (end == -1) {
            buf.append(body.substring(start));
            return;
         }
         if (end > 0 && body.charAt(end - 1) == '\r')
            buf.append(body.substring(start, end - 1));
         else
            buf.append(body.substring(start, end));
         buf.append("%0A");
         start = end + 1;
      }
   }
}
