package explorer;


import java.io.File;
import org.eclipse.jface.viewers.*;

public class AllowOnlyFoldersFilter extends ViewerFilter
{
  public boolean select(Viewer viewer, Object parent, Object element)
  {
    return ((File) element).isDirectory();
  }
}
