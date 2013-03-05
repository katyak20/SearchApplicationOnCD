package lucene_search;


import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

public class SearchTableLabelProvider implements ITableLabelProvider
{
	public String getColumnText(Object element, int column_index)
	  {
		SearchResults result =  (SearchResults) element;
		
		
	    if (column_index == 0)
	    {
	      return  result.getName();
	    }
	    if (column_index == 1)
	    {
	      return "" +  result.getScore() + "%";
	    }
	    if (column_index == 2)
	    {
	      return "" + result.getCreationDate();
	
	    }
	    if (column_index == 3)
	    {
	      return "" + result.getAbsolutePath();
	    }
	    if (column_index == 4)
	    {
	      return "" + result.getSummary();
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

   return null;
  }

}


