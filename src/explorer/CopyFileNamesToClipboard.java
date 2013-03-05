package explorer;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import application.MyApplicationWindow;

import org.eclipse.swt.dnd.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;

public class CopyFileNamesToClipboard extends Action
{
  MyApplicationWindow window;

  public CopyFileNamesToClipboard(MyApplicationWindow w)
  {
    window = w;
    setToolTipText("Copy absolute file names of selected files to the clipboard");
    setText("Copy File &Names@Ctrl+Shift+C");
    setImageDescriptor( ImageDescriptor.createFromURL(newURL("file:icons/copy.gif")));
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
   Clipboard clipboard = Util.getClipboard();
    TextTransfer text_transfer = TextTransfer.getInstance();
    
    MyFileExplorer explorer = window.getFileExplorer();
    IStructuredSelection selection = explorer.getTableSelection();
    if (!selection.isEmpty())
    {
      
    StringBuffer string_buffer = new StringBuffer();
    for (Iterator<?> i = selection.iterator(); i.hasNext();)
    {
      File file = (File) i.next();
      string_buffer.append(" ");
      string_buffer.append(file.getAbsolutePath());
    }
    window.setStatus(string_buffer.toString());

    clipboard.setContents(
      new Object[] { string_buffer.toString()},
      new Transfer[] { text_transfer });
  }
  }
}
