package explorer;

import java.net.MalformedURLException;
import java.net.URL;

import application.MyApplicationWindow;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;

public class ExitAction extends Action
{
  MyApplicationWindow window;

  public ExitAction(MyApplicationWindow w)
  {
    window = w;
    setText("E&xit@Ctrl+W");
    setToolTipText("Exit the application");
    setImageDescriptor(
    		ImageDescriptor.createFromURL(newURL("file:icons/close.gif")));
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
    window.close();
  }
}
