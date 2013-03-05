package lucene_search;


import java.util.Vector;
import org.eclipse.jface.viewers.*;
public class SearchTableContentProvider implements IStructuredContentProvider
{
  public Object[] getElements(Object element)
  {
    return ((Vector<?>) element).toArray();
  }

  public void dispose()
  {
  }

  public void inputChanged(Viewer viewer, Object old_object, Object new_object)
  {
  }
}