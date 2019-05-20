package rfid;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.json.JSONObject;

public class Reader implements KeyListener{

	protected Shell shell;
	private Text txtSwipeToRegister;
	private Label lblRegisterId;
	private Label lblName;
	private Text text_1;
	private Table table;
	private TableColumn tblclmnName;
	private TableColumn tblclmnPresent;
	List list;
	private String newUser = "";
	private String markUser = "";
	
	// My variables
	private HashMap<String, String> users; // User ID > User Name
	private HashMap<String, Boolean> markoff; // User ID > Present
	private Text txtSwipeToMark;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Reader window = new Reader();
			
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 354);
		shell.setText("SWT Application");
		
		txtSwipeToRegister = new Text(shell, SWT.BORDER);
		txtSwipeToRegister.setEditable(false);
		txtSwipeToRegister.setText("Swipe to register");
		txtSwipeToRegister.setBounds(75, 21, 104, 19);
		txtSwipeToRegister.addKeyListener(this);
		
		Label lblEnterId = new Label(shell, SWT.NONE);
		lblEnterId.setBounds(10, 24, 59, 14);
		lblEnterId.setText("Swipe");
		
		lblRegisterId = new Label(shell, SWT.NONE);
		lblRegisterId.setBounds(30, 1, 59, 14);
		lblRegisterId.setText("Register ID");
		
		lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(10, 54, 59, 14);
		lblName.setText("Name");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(75, 49, 104, 19);
		
		list = new List(shell, SWT.BORDER);
		list.setBounds(10, 153, 126, 152);
		
		Label lblRegisteredUsers = new Label(shell, SWT.NONE);
		lblRegisteredUsers.setBounds(17, 123, 126, 24);
		lblRegisteredUsers.setText("Registered Users");
		
		Button btnRegister = new Button(shell, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				users.put(newUser, text_1.getText());
				markoff.put(newUser, false);
				
			    
				refreshListAndTable();
				
				
				/*
				
			    */
			}
		});
		btnRegister.setBounds(10, 87, 94, 27);
		btnRegister.setText("Register");
		
		Label lblRollMarking = new Label(shell, SWT.NONE);
		lblRollMarking.setBounds(241, 1, 94, 14);
		lblRollMarking.setText("Roll Marking");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(220, 87, 205, 173);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		tblclmnPresent = new TableColumn(table, SWT.NONE);
		tblclmnPresent.setWidth(100);
		tblclmnPresent.setText("Present");
		
		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setBounds(245, 21, 134, 27);
		
		txtSwipeToMark = new Text(shell, SWT.BORDER);
		txtSwipeToMark.setEditable(false);
		txtSwipeToMark.setText("Swipe to mark off");
		txtSwipeToMark.setBounds(243, 49, 117, 19);
		
		Button btnJsonTest = new Button(shell, SWT.NONE);
		btnJsonTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// https://www.baeldung.com/java-http-request
				
				
				try {
					String url = "http://107.21.194.196:3000/students";
					HttpMethods.sendGet(url, "");
				} catch (Exception error) {
					// TODO Auto-generated catch block
					error.printStackTrace();
				}
				
				
				
				Map<String, String> map = new HashMap<>();
				map.put("name", "jon doe");
				map.put("age", "22");
				map.put("city", "chicago");
				JSONObject jo = new JSONObject(map); 
				// https://stackoverflow.com/questions/53340128/how-to-perform-a-post-request-using-json-file-as-body
			}
		});
		btnJsonTest.setBounds(30, 311, 106, 27);
		btnJsonTest.setText("Get Students");
		
		Button btnPostRoll = new Button(shell, SWT.NONE);
		btnPostRoll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String url = "http://107.21.194.196:3000/echo_post";
					//String url = "http://localhost:3000/echo_post";
					Map<String, String> map = new HashMap<>();
					map.put("name", "jon doe");
					map.put("age", "22");
					map.put("city", "chicago");
					JSONObject jo = new JSONObject(map); 
					System.out.println(jo.toString());
					//HttpMethods.sendPost(url, "", jo.toString());
					String res = HttpMethods.sendPostHttpClient(url, jo.toString());
					System.out.println(res);
				} catch (Exception error) {
					// TODO Auto-generated catch block
					error.printStackTrace();
				}
			}
		});
		btnPostRoll.setBounds(159, 311, 94, 27);
		btnPostRoll.setText("Post roll");
		txtSwipeToMark.addKeyListener(this);
		
		users = new HashMap<String, String>();
		markoff = new HashMap<String, Boolean>();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.print(e.character);
		if (e.getSource() == txtSwipeToRegister) {
			if (e.keyCode == 13) {
				System.out.println("New user: " + newUser);
				text_1.setFocus();
			} else {
				if (newUser.length() >= 10) {
					newUser = "";
				}
				newUser += e.character;
			}
		} else if (e.getSource() == txtSwipeToMark) {
			if (e.keyCode == 13) {
				markoff.replace(markUser, true);
				System.out.println(markoff.get("markUser"));
				refreshListAndTable();
				System.out.println("Mark user: " + markUser);
				
				
			} else {
				if (markUser.length() >= 10) {
					markUser = "";
				}
				markUser += e.character;
				
			}
			
			
			
		}
		
		
		
		
	}
	
	private void refreshListAndTable() {
		list.setItems(users.values().toArray(list.getItems()));
		
		table.removeAll();
		Iterator it = markoff.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        TableItem item = new TableItem(table, SWT.NULL);
			item.setText(0, users.get(pair.getKey()));
			item.setText(1, pair.getValue().toString());
	        
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
