package explorer;


import java.net.MalformedURLException;
import java.net.URL;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.dnd.*;

public class Util
{
  private static ImageRegistry image_registry;
  private static Clipboard clipboard;


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

  public static ImageRegistry getImageRegistry()
  {
    if (image_registry == null)
    {
      image_registry = new ImageRegistry();
      image_registry.put(
        "folder",
        ImageDescriptor.createFromURL(newURL("file:icons/folder.png")));
      image_registry.put(
        "file",
        ImageDescriptor.createFromURL(newURL("file:/icons/file.png")));
      image_registry.put(
    	        "pdf",
    	        ImageDescriptor.createFromURL(newURL("file:icons/pdf.png")));
      image_registry.put(
  	        "separator",
  	        ImageDescriptor.createFromURL(newURL("file:icons/i-node.png")));
      image_registry.put(
    	        "search",
    	        ImageDescriptor.createFromURL(newURL("file:icons/Search_32.png")));
      image_registry.put(
  	        "explorer",
  	        ImageDescriptor.createFromURL(newURL("file:icons/folder-open.png")));
      image_registry.put(
    	        "open",
    	        ImageDescriptor.createFromURL(newURL("file:icons/Run2_32.png")));}
    return image_registry;
  }
 public static Clipboard getClipboard()
  {
    if (clipboard == null)
    {
      clipboard = new Clipboard(Display.getCurrent());
    }

    return clipboard;
  }

}
