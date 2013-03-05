package explorer;


import java.io.File;



import application.MyApplicationWindow;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.program.*;

public class MyFileExplorer extends SashForm implements ISelectionChangedListener
{
	
	  private TreeViewer tv;
	  private IStructuredSelection selection;
	  private MyApplicationWindow window;
	  private TableViewer tbv;

  public MyFileExplorer(Composite parent, MyApplicationWindow w) {
	    super(parent, SWT.HORIZONTAL | SWT.NULL);
	    
	window = w;
    window.setStatus("");
    tv = new TreeViewer(this);
    tv.setContentProvider(new FileTreeContentProvider());
    tv.setLabelProvider(new FileTreeLabelProvider());
    tv.setInput(new File("/"));
    tv.addFilter(new AllowOnlyFoldersFilter());

   tbv =
      new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
    tbv.setContentProvider(new FileTableContentProvider());
    tbv.setLabelProvider(new FileTableLabelProvider());
    tbv.setSorter(new FileSorter());
    tbv.addFilter(new AllowOnlyFilesFilter());



    TableColumn column = new TableColumn(tbv.getTable(), SWT.LEFT);
    column.setText("Name");
    column.setWidth(270);

    column = new TableColumn(tbv.getTable(), SWT.CENTER);
    column.setText("Size");
    column.setWidth(100);
    
    column = new TableColumn(tbv.getTable(), SWT.RIGHT);
    column.setText("LastUpdated");
    column.setWidth(100);

    tbv.getTable().setHeaderVisible(true);

    tv.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent event)
      {
        IStructuredSelection selection =
          (IStructuredSelection) event.getSelection();

        Object selected_file = selection.getFirstElement();
        tbv.setInput(selected_file);
      }
    });
   

    tbv.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent event)
      {
        IStructuredSelection selection =
          (IStructuredSelection) event.getSelection();
        
      window.setStatus("Number of items selected is " + selection.size());
      
      }
    });
   tbv.addDoubleClickListener(new IDoubleClickListener() {
	   public void doubleClick(DoubleClickEvent event) {
		   IStructuredSelection selection =
		          (IStructuredSelection) event.getSelection();
		        File selected_file = (File) selection.getFirstElement();
		        if (selected_file.isFile())
		        {
		          Program.launch(selected_file.getAbsolutePath());
		        }
		        
	   }
   });
   tbv.addSelectionChangedListener(this);
 
  }
 
public void setInput(String path) {
	
	System.out.println(path);
	tv.setInput(new File(path));
	File selected_file = new File(path); 
	tbv.setInput((Object)selected_file);
	
	
}
  

public IStructuredSelection getTableSelection()
  {   
	return selection;  
  }
  
  public void selectionChanged(SelectionChangedEvent event) {

	         selection = (IStructuredSelection) event.getSelection();
	         System.out.println("Is selection empty?  "+ selection.isEmpty());
			  
 }
		
		
	
  public void openFolder(File folder)
  {
    tv.setExpandedState(folder, true);
    tv.setSelection(new StructuredSelection(folder), false);
  }
  
 


}
