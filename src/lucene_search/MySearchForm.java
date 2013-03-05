package lucene_search;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import java.io.*;
import java.util.Vector;
import org.pdfbox.util.PDFHighlighter;
import org.pdfbox.pdmodel.PDDocument;




import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.program.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import application.MyApplicationWindow;




//import application.MyApplicationWindow;


public class MySearchForm extends Composite implements SelectionListener {
	
	private final static String xmlPath = "C://Java/my.xml";
    
	private Text search_criteria;
	private Vector<SearchResults> results;
	private StringBuffer messages;
	private TableViewer tbv;
	private Shell shell;
	
	private String mySearchParameter;
	private MyApplicationWindow window;
	
	public MySearchForm(Composite parent, MyApplicationWindow w) {
	    super(parent, SWT.BORDER);
	   
	    final Display display = Display.getCurrent();
        shell = display.getActiveShell();
        
        window = w;
	    window.setStatus("");
	    Color red = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
	    Font font = new Font(display, "Courier New", 9, SWT.NORMAL);
	    
	    final int insetX = 2, insetY = 2;
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = insetX;
		formLayout.marginHeight = insetY;
		this.setLayout (formLayout);
		
		FormData searchPanelData = new FormData();
	    searchPanelData.left    = new FormAttachment(0,insetX);
	    searchPanelData.right   = new FormAttachment(25,-insetX);
	    searchPanelData.top = new FormAttachment(0,insetY);
	    searchPanelData.bottom = new FormAttachment(100,insetY);
	    
	    Composite searchPanel = new Composite(this, SWT.CENTER); 
       
        searchPanel.setBackground(red);
        FormLayout searchLayout= new FormLayout();

        
        searchPanel.setLayout(searchLayout); 
        searchPanel.setLayoutData(searchPanelData);
        
	    
        Label label2 = new Label(searchPanel,SWT.CENTER); 
        label2.setText("Please, enter your search:");
        label2.setBackground(red);
        label2.setFont(font);
        
        FormData myFormData = new FormData();
        myFormData.left = new FormAttachment(0,5);
        myFormData.right = new FormAttachment(100,-5);
        myFormData.top = new FormAttachment(2,5);
        label2.setLayoutData(myFormData);
        
        search_criteria = new Text(searchPanel, SWT.SINGLE | SWT.BORDER);
        search_criteria.setBounds(10, 85, 100, 32);
        search_criteria.setText("");
        
        myFormData = new FormData();
        myFormData.left = new FormAttachment(0,5);
        myFormData.right = new FormAttachment(100,-5);
        myFormData.top = new FormAttachment(label2,5);
        search_criteria.setLayoutData(myFormData);
        
        Button searchButton = new Button(searchPanel,SWT.PUSH); 
        searchButton.setText("Search"); 
        
        myFormData = new FormData();
        myFormData.left = new FormAttachment(0,5);
        myFormData.right = new FormAttachment(100,-5);
        myFormData.top = new FormAttachment(search_criteria,5);
        searchButton.setLayoutData(myFormData);
        
        searchButton.addSelectionListener(this);
        
        FormData outcomeData = new FormData();
	    outcomeData.top = new FormAttachment(0, insetY);
	    outcomeData.bottom = new FormAttachment(100, -insetY);
	    outcomeData.right = new FormAttachment(100, -insetX);
	    outcomeData.left = new FormAttachment(searchPanel, insetX);
       
      
       
	    tbv =
            new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
	    
          tbv.setContentProvider(new SearchTableContentProvider());
          tbv.setLabelProvider(new SearchTableLabelProvider());
          //tbv.setSorter(new FileSorter());
          Table table = tbv.getTable();
          TableColumn column1 = new TableColumn(table, SWT.LEFT);
          column1.setText("Name");
          column1.setWidth(400);
          TableColumn column3 = new TableColumn(table, SWT.CENTER);
          column3.setText("Score");
          column3.setWidth(50);
          TableColumn column4 = new TableColumn(table, SWT.RIGHT);
          column4.setText("CreationDate");
          column4.setWidth(150);
         
          table.setHeaderVisible(true);
          table.setLinesVisible(true);
          table.setLayoutData(outcomeData);
          tbv.addDoubleClickListener(new IDoubleClickListener() {
       	   public void doubleClick(DoubleClickEvent event) {
         
       		FileOutputStream fos =null;
       		PrintWriter xmlWriter = null;
       		PDFHighlighter highlighter = null;
      		
    		   IStructuredSelection selection =
    		          (IStructuredSelection) event.getSelection();
    		      System.out.println( selection.getFirstElement().toString());
    		      //myPath = selection.getFirstElement().toString();
    		      SearchResults selected_file = (SearchResults) selection.getFirstElement();
    		      
    		     String path =  selected_file.getAbsolutePath();
    		     
    		     int pathLength = path.length();
    		     String fileExtension = path.substring(pathLength-3, pathLength);
    		     System.out.println(fileExtension);
    		         if (fileExtension.equals("pdf")) {
    		        	 System.out.println("PDF");
    		        	 try {
    		        		 fos = new FileOutputStream(xmlPath);
    		        	     xmlWriter = new PrintWriter(fos);
    		        	     highlighter = new PDFHighlighter();
    		        	 File file = new File(path);
    		        	 
    		        	 PDDocument pdDocument = PDDocument.load(file);
    		        	 highlighter.generateXMLHighlight( pdDocument, mySearchParameter, xmlWriter);
    		           
    		        	String myPath = path
    		                             +"#search="+ mySearchParameter ; //file:download/MySearchApplication/my.xml";*/
    		        	 //"file:icons/folder.png"
    		        	// ChildShell cs = new ChildShell(shell, myPath);	
    		        	// System.out.println("ChildShell" + cs.toString());
    		        	 FileBrowser browser = new FileBrowser(shell, myPath);
    		        	 System.out.println("FileBrowser" + browser.toString());
    		        	 fos.close();
    		        	 xmlWriter.close();
    		        	 pdDocument.close();
    		        	 
    		        	 }
    		        	 catch(IOException e) {
    		        		 System.out.println("Problem occurred in I/O");
    		        	 }
    		        	
    		         }
    		         else
    		        	 {Program.launch(selected_file.getAbsolutePath());
    		        	 }
    	   }
       });
      
          TableListener tableListener = new TableListener(tbv,shell,display) ;
         
          table.addListener(SWT.Dispose, tableListener);
          table.addListener(SWT.KeyDown, tableListener);
          table.addListener(SWT.MouseMove, tableListener);
          table.addListener(SWT.MouseHover, tableListener);

      searchPanel.pack();
      this.pack();
	    
	 
	   
	    
	}
	


		public void widgetSelected(SelectionEvent e) { 
			
			mySearchParameter = search_criteria.getText();
			System.out.println(search_criteria.getText());
       	 SearchPDFFiles searchFiles = new SearchPDFFiles(shell);
       	 searchFiles.search(mySearchParameter);
       	 results = searchFiles.getSearchResults();
       	 System.out.println("Results" + results.size() +results.toString());
       	 
       	 
       	 tbv.setInput(results);
       	 
       	 messages =searchFiles.getMessage();
		   
		     System.out.println(messages);
		     

        } 
		public void widgetDefaultSelected(SelectionEvent e){
			
		}
		
	}

class LabelListener implements Listener {
  Table currentTable;
  Shell myTipShell;
  
	public LabelListener(Table myTable, Shell myTip)  {
		myTipShell = myTip;
		currentTable = myTable; 
	}
    public void handleEvent(Event event) {
      Label label = (Label) event.widget;
      Shell shell = label.getShell();
      switch (event.type) {
      case SWT.MouseDown:
        Event e = new Event();
        e.item = (TableItem) label.getData("_TABLEITEM");
        // Assuming table is single select, set the selection as if
        // the mouse down event went through to the table
        currentTable.setSelection(new TableItem[] { (TableItem) e.item });
        currentTable.notifyListeners(SWT.Selection, e);
      // fall through
      case SWT.MouseExit:
        shell.dispose();
        break;
      }
    }
  };
 class TableListener implements Listener {
    Shell tip = null;

    Label label = null;
    TableViewer tableViewer;
    Shell shell = null;
    Display display;
    
  public TableListener(TableViewer tbv, Shell myShell, Display myDisplay) {
	  tableViewer = tbv;
	  display = myDisplay;
	  shell = myShell;
  }
  

    public void handleEvent(Event event) {
      switch (event.type) {
      case SWT.Dispose:
      case SWT.KeyDown:
      case SWT.MouseMove: {
        if (tip == null)
          break;
        tip.dispose();
        tip = null;
        label = null;
        break;
      }
      case SWT.MouseHover: {
        Table table = tableViewer.getTable();
        TableItem item = table.getItem(new Point(event.x, event.y));
        if (item != null) {
          if (tip != null && !tip.isDisposed())
            tip.dispose();
          tip = new Shell(shell, SWT.ON_TOP | SWT.TOOL);
          tip.setLayout(new FillLayout());
          label = new Label(tip, SWT.NONE);
          label.setForeground(display
              .getSystemColor(SWT.COLOR_INFO_FOREGROUND));
          label.setBackground(display
              .getSystemColor(SWT.COLOR_INFO_BACKGROUND));
          label.setData("_TABLEITEM", item);
          SearchResults myResults = (SearchResults)item.getData();
          String textForTip = myResults.getSummary();
          label.setText("DOUBLE CLICK TO OPEN   " + textForTip);
          label.addListener(SWT.MouseExit, new LabelListener(table,tip));
          label.addListener(SWT.MouseDown, new LabelListener(table,tip));
          Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
          Rectangle rect = item.getBounds(0);
          Point pt = table.toDisplay(rect.x+200, rect.y);
          tip.setBounds(pt.x, pt.y, size.x, size.y);
          tip.setVisible(true);
        }
      }
      }
    }
  };
