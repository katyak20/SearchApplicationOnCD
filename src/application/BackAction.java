package application;

import java.net.MalformedURLException;
import java.net.URL;

import explorer.Util;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;

public class BackAction extends Action //implements ISelectionChangedListener

{
  MyApplicationWindow window;

  public BackAction(MyApplicationWindow w)
  {
    window = w;
    setText("Back");
    setToolTipText("Back to the root");
    setImageDescriptor(
    		ImageDescriptor.createFromURL(newURL("file:icons/Home_32x32.png")));
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


 public void run()
  {

	 window.getFileExplorer().setInput("/");
     
    
  }


  
}
