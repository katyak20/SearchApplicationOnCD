package explorer;

import java.io.File;
import org.eclipse.jface.viewers.*;

public class FileSorter extends ViewerSorter
{
  public int category(Object element)
  {
    return ((File) element).isDirectory() ? 0 : 1;
  }
}
