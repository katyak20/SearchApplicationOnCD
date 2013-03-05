package lucene_index;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.Font;


import application.MyApplicationWindow;
import application.WarningMessageBox;

public class MyIndexForm extends Composite implements SelectionListener{
	
	private Text indexOutcome ;
	private MyApplicationWindow window;
	private Text textDirectoryToIndex;
	
	public MyIndexForm(Composite parent, MyApplicationWindow w) {
		super(parent, SWT.BORDER);
		
		Display display = Display.getCurrent();
	 //   Shell	 myShell =display.getActiveShell();
	//	Color gray = display.getSystemColor(SWT.COLOR_GRAY);
 		Color red = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
 		window = w;
		window.setStatus("");
		this.setBackground(red);
		Font font = new Font(display, "Courier New", 9, SWT.NORMAL);

		this.setLayout(new FormLayout());
	      Text instructionsLabel = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.CENTER);
	      instructionsLabel.setText("This utility will index HTML, HTM, " +
	      		      "  TXT and PDF files " +"and will store the index in the 'MyDocumentsIndex' directory on your computer. " +
	      		    "\n"+ " If the index already exists the program will update it." + "\n " + "Example (Windows)  -  " +"C:\\MyDocuments"+
	      		  "\n"+"Example (Mac OS)  -  "  + "/users/myName/Documents" ); 
	      instructionsLabel.setBackground(red);
	      instructionsLabel.setFont(font);
	      FormData myFormData = new FormData();
	      myFormData.left = new FormAttachment(0,5);
	      myFormData.right = new FormAttachment(100,-5);
	      myFormData.top = new FormAttachment(0,5);
	      instructionsLabel.setLayoutData(myFormData);
	      
	      Label pleaseLabel= new Label (this, SWT.WRAP | SWT.CENTER);
	      pleaseLabel.setText("Enter the directory to index:");
	      pleaseLabel.setBackground(red);
	      pleaseLabel.setFont(font);
	      
	      myFormData = new FormData();
	      myFormData.left = new FormAttachment(3,5);
	      myFormData.right = new FormAttachment(24,-5);
	      myFormData.top = new FormAttachment(instructionsLabel,5);
	      pleaseLabel.setLayoutData(myFormData);
	      
	      textDirectoryToIndex = new Text (this, SWT.SINGLE | SWT.BORDER );
	      textDirectoryToIndex.setText("/MyDocuments");
	      
	      myFormData = new FormData();
	      myFormData.left = new FormAttachment(25,5);
	      myFormData.right = new FormAttachment(75,-5);
	      myFormData.top = new FormAttachment(instructionsLabel,5);
	      textDirectoryToIndex.setLayoutData(myFormData);
	      
	      Button myIndexButton = new Button(this, SWT.PUSH);
	      myIndexButton.setText("Index");
	      
	      myFormData = new FormData();
	      myFormData.left = new FormAttachment(80,5);
	      myFormData.right = new FormAttachment(97,-5);
	      myFormData.top = new FormAttachment(instructionsLabel,5);
	      myIndexButton.setLayoutData(myFormData);
	      myIndexButton.addSelectionListener(this);
	      
	      
	      
	      indexOutcome = new Text(this, SWT.MULTI | SWT.BORDER);
	      myFormData = new FormData();
	      myFormData.top = new FormAttachment(myIndexButton,5);
	      myFormData.bottom = new FormAttachment(
	         100,-5);
	      myFormData.left = new FormAttachment(0,5);
	      myFormData.right = new FormAttachment(100,-5);
	      indexOutcome.setLayoutData(myFormData);
}
	 public void widgetSelected(SelectionEvent e) { 
         
		  IndexFiles indexFiles = new IndexFiles();
		  String directoryToIndex = textDirectoryToIndex.getText()+"/";
		  String indexDestination = directoryToIndex + "MyDocumentsIndex";
          indexFiles.index(new File(directoryToIndex), true, indexDestination); 
          indexOutcome.setText(indexFiles.getMessage().toString());
          window.getFileExplorer().setInput(directoryToIndex);
          try {
             PrintWriter pw = new PrintWriter(new FileWriter("directoryToIndex.txt"));
             pw.println(indexDestination);
             pw.close();
          }
          catch(IOException e1) {
        	  WarningMessageBox messageBox=new WarningMessageBox(window.getShell(),"Trouble settingup the file with index directory file-path.");
          }
          
      }
     
	 public void widgetDefaultSelected(SelectionEvent e) { 
	 }
	 
}