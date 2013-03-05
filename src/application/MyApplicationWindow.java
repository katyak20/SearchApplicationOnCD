package application;

import lucene_index.MyIndexForm;
import lucene_search.MySearchForm;
import explorer.CopyFileNamesToClipboard;
import explorer.ExitAction;
import explorer.MyFileExplorer;
import explorer.OpenAction;
import explorer.Util;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class MyApplicationWindow  extends ApplicationWindow{

	private ExitAction exit_action;
	private BackAction back_action;
	private OpenAction open_action;
	private OpenEmailAction email_handler;
	private MyFileExplorer file_explorer;
    private MySearchForm search_form;
    private MyIndexForm  index_form;
	private Shell s;
	private  TabFolder tabFolder;
	
	public MyApplicationWindow() {
		super(null);
	
		exit_action = new ExitAction(this);
		back_action = new BackAction(this);
		open_action = new OpenAction(this);
		email_handler = new OpenEmailAction(this);
		addStatusLine();
	    addMenuBar();
	    addToolBar(SWT.FLAT | SWT.WRAP);
	   
	}
	
	protected Control createContents(Composite parent)
	  {
		s = getShell();
		this.setStatus("HELLO");
	    s.setText("AVTEC Search Engine");
	    tabFolder = new TabFolder (s, SWT.BORDER);
	    for (int i=0; i<3; i++) {
	     switch (i) {	
	     case 1 : {
	    	TabItem item = new TabItem (tabFolder, SWT.NONE);
	    	//item.setImage(Util.getImageRegistry().get("search"));
			item.setText ("      Search Engine      ");
			item.setImage(Util.getImageRegistry().get("search"));
			search_form = new MySearchForm(tabFolder, this);
			item.setControl(search_form);
			break;
	     }
	     case 0 :{ 	 
	        TabItem item = new TabItem (tabFolder, SWT.NONE);
			item.setText ("Contents of your AVTEC CD" );
			item.setImage(Util.getImageRegistry().get("explorer"));
			file_explorer = new MyFileExplorer(tabFolder,this);
			item.setControl(file_explorer);
			break;
		 }
	     case 2: {
	    	TabItem item = new TabItem (tabFolder, SWT.NONE);
			item.setText ("   Indexing facility      ");
			index_form = new MyIndexForm(tabFolder, this);
			item.setControl(index_form);
	    	 break;
	     }
	     }
		}
		tabFolder.pack ();
		s.pack ();
		s.setSize(900, 700);
	    return tabFolder;
	   
	  }

	public static void main(String[] args)
	  {
	    MyApplicationWindow w = new MyApplicationWindow();
	    
	    w.setBlockOnOpen(true);
	    
	    w.open();
	    Display.getCurrent().dispose();
	    Util.getClipboard().dispose();

	  }
	
	public MyFileExplorer getFileExplorer() {
		return file_explorer;
	}
	
	  protected MenuManager createMenuManager()
	  {
	    MenuManager bar_menu = new MenuManager("");

	    MenuManager file_menu = new MenuManager("&File");
	    MenuManager edit_menu = new MenuManager("&Edit");
	    MenuManager view_menu = new MenuManager("&View");

	    bar_menu.add(file_menu);
	    bar_menu.add(edit_menu);
	    bar_menu.add(view_menu);

	    
	   
	    edit_menu.add(new OpenAction(this));
	    file_menu.add(new ExitAction(this));

	    return bar_menu;
	  }
	  
	  protected ToolBarManager createToolBarManager(int style)
	  {

	    ToolBarManager tool_bar_manager = new ToolBarManager(style);

	    
	    tool_bar_manager.add(email_handler);
	    tool_bar_manager.add(back_action);
	    tool_bar_manager.add(open_action);
	    tool_bar_manager.add(exit_action);
	    return tool_bar_manager;
	  }
      
	  

	
}
