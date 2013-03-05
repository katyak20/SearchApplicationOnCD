package lucene_search;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.browser.*;


//import sun.net.ProgressEvent;
//import sun.net.ProgressListener;

import explorer.Util;

public class FileBrowser {
	
	private String UrlWithHighlighter;
	//public static void main(String [] args) {
	public FileBrowser(Shell parent, String myUrl) {
   	 UrlWithHighlighter= myUrl;
	    Shell shell = new Shell(parent);
	  
		
		
		//shell.setSize(650,800);
		shell.setLocation(901,5);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);
		ToolBar toolbar = new ToolBar(shell, SWT.BORDER);
		ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
		itemBack.setText("Back");
		

	  /*  ToolItem separationImage1 = new ToolItem(toolbar, SWT.NONE);
	    separationImage1.setImage(Util.getImageRegistry().get("separator"));
	    */
		ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
		itemForward.setText("Forward");
		
      /*  ToolItem separationImage2 = new ToolItem(toolbar, SWT.NONE);
	    separationImage2.setImage(Util.getImageRegistry().get("separator"));
	   */ 
		ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
		itemStop.setText("Stop");

	   /* ToolItem separationImage3 = new ToolItem(toolbar, SWT.NONE);
	    separationImage3.setImage(Util.getImageRegistry().get("separator"));
	   */ 
		ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
		itemRefresh.setText("Refresh");

	  /*  ToolItem separationImage4 = new ToolItem(toolbar, SWT.NONE);
	    separationImage4.setImage(Util.getImageRegistry().get("separator"));
	    */
		ToolItem itemGo = new ToolItem(toolbar, SWT.PUSH);
		itemGo.setText("Go");

	  /*  ToolItem separationImage5 = new ToolItem(toolbar, SWT.NONE);
	    separationImage5.setImage(Util.getImageRegistry().get("separator"));
	    */
		ToolItem itemAvtec = new ToolItem(toolbar, SWT.PUSH);
	    itemAvtec.setText("AVTEC Website");
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		toolbar.setLayoutData(data);

		Label labelAddress = new Label(shell, SWT.NONE);
		labelAddress.setText("Address");
		
		final Text location = new Text(shell, SWT.BORDER);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		location.setLayoutData(data);

		final Browser browser;
		try {
			browser = new Browser(shell,  SWT.RESIZE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			//display.dispose();
			return;
		}
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		browser.setLayoutData(data);

		final Label status = new Label(shell, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		status.setLayoutData(data);

		final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		progressBar.setLayoutData(data);

		/* event handling */
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				ToolItem item = (ToolItem)event.widget;
				String string = item.getText();
				if (string.equals("Back")) browser.back(); 
				else if (string.equals("Forward")) browser.forward();
				else if (string.equals("Stop")) browser.stop();
				else if (string.equals("Refresh")) browser.refresh();
				else if (string.equals("Go")) browser.setUrl(location.getText());
				else if (string.equals("AVTEC Website")) browser.setUrl("http://www.avtecsupport.com");
		   }
		};
		/*browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
					if (event.total == 0) return;                            
					int ratio = event.current * 100 / event.total;
					progressBar.setSelection(ratio);
			}
			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		});*/
		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				status.setText(event.text);	
			}
		});
		browser.addLocationListener(new LocationListener() {
			public void changed(LocationEvent event) {
				if (event.top) location.setText(event.location);
			}
			public void changing(LocationEvent event) {
			}
		});
		itemBack.addListener(SWT.Selection, listener);
		itemForward.addListener(SWT.Selection, listener);
		itemStop.addListener(SWT.Selection, listener);
		itemRefresh.addListener(SWT.Selection, listener);
		itemGo.addListener(SWT.Selection, listener);
		itemAvtec.addListener(SWT.Selection, listener);
		location.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				browser.setUrl(location.getText());
			}
		});
		
		shell.open();
		browser.setUrl(UrlWithHighlighter);
		
		/*while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
*/
	}
}
