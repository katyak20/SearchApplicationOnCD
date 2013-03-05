package explorer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import application.MyApplicationWindow;
import application.WarningMessageBox;
import org.eclipse.swt.program.*;


public class OpenAction extends Action //implements ISelectionChangedListener

{
  MyApplicationWindow window;

  public OpenAction(MyApplicationWindow w)
  {
    window = w;
    setText("Run");
    setToolTipText("Run the associated program on a file");
    setImageDescriptor(
    	      ImageDescriptor.createFromURL(newURL("file:icons/launch_run.gif")));
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
  {MyFileExplorer explorer = window.getFileExplorer();
  try {
         IStructuredSelection selection = explorer.getTableSelection();
         if (selection.size() != 1)
            return;

         File selected_file = (File) selection.getFirstElement();
         if (selected_file.isFile())
            {
    	       // Program.launch("file:///download/test/references.pdf#search='projects'");//+"#xml=file:///download/MySearchApplication/my.xml");
                Program.launch(selected_file.getAbsolutePath());
             }
      }
   catch (NullPointerException e) {
	   WarningMessageBox messageBox=new WarningMessageBox(window.getShell(),"Please, choose the file to open in the right hand window");
   }
   
  }


  
}

