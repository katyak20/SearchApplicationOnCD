package application;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class WarningMessageBox {
	
  private	String message;
  MyApplicationWindow window;
  
  public WarningMessageBox(Shell parent, String m) {
	  Shell child = new Shell(parent);
	  child.setLocation(400, 300);
       message = m;
    
    
    MessageBox messageBox = new MessageBox(child, SWT.ICON_WARNING |SWT.OK);
    messageBox.setMessage(message);
   
   
    int rc = messageBox.open();
    
    System.out.println(rc == SWT.OK);
   
   // child.open();
  }
}