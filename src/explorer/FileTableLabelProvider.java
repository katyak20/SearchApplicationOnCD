package explorer;

import java.io.File;
import java.util.Date;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

public class FileTableLabelProvider implements ITableLabelProvider
{
	public String getColumnText(Object element, int column_index)
	  {
	    if (column_index == 0)
	    {
	      return ((File) element).getName();
	    }

	    if (column_index == 1)
	    {
	      return "" + ((File) element).length();
	    }
	    if (column_index == 2)
	    {
	      return "" + new Date(((File) element).lastModified());
	    }
	  

	    return "";
	  }

  public void addListener(ILabelProviderListener ilabelproviderlistener)
  {
  }

  public void dispose()
  {
  }

  public boolean isLabelProperty(Object obj, String s)
  {
    return false;
  }

  public void removeListener(ILabelProviderListener ilabelproviderlistener)
  {
  }
  
  public Image getColumnImage(Object element, int column_index)
  {

    if (column_index != 0)
    {
      return null;
    }

    if (((File) element).isDirectory())
    {
      return Util.getImageRegistry().get("folder");
    }
    else
    {
    	
    	String path =  ((File) element).getName();
    	int pathLength = path.length();
	    String fileExtension = path.substring(pathLength-3, pathLength);
    	  if (fileExtension.equals("pdf"))
    	  {
    		  return Util.getImageRegistry().get("pdf");
    	  }
    	  else
    	    {
	         return Util.getImageRegistry().get("file");
    	    }
    }
  }

}

