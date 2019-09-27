package GroupA;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class SixNationsPredictionsGUI extends JFrame {
	
	File workingDirectory = new File(System.getProperty("user.dir"));

	JTable myTable;
	
	// Menu structure
	JMenuBar myBar;
	JMenu fileMenu, recordMenu,  exitMenu;
	JMenuItem fileLoad, fileSaveAs, addPrediction, removePrediction, exitProgram;
	
	
	//JFileChooser
	private JFileChooser jfc = new JFileChooser();
	
	private String[] teams = new String[] {
			"Ireland", "England", "Wales", "France", "Scotland", "Italy"
	};
	
	// JComboBoxes
	private JComboBox teamComboBoxColumn = new JComboBox(teams);
	private JComboBox teamComboBox = new JComboBox(teams);

	JButton btnSearch = new JButton("Search");
	JButton btnShowAll = new JButton("Show All");
	
	JPanel topPane;

	MyTableModel tm;
	JScrollPane myPane;
	
	ArrayList<MyPrediction> predictions = new ArrayList<MyPrediction>(); 
	
	// Used to indicate whether data is already in a file
	File currentFile;
	
	public SixNationsPredictionsGUI(){  
		// Setting up menu
		createMenuBar();

		topPane = new JPanel();

		tm = new MyTableModel(predictions);
		myTable = new JTable(tm);

		myTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		myPane = new JScrollPane(myTable);
		myTable.setSelectionForeground(Color.white);
		myTable.setSelectionBackground(Color.red);
		myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		myTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(teamComboBoxColumn));
		myTable.setAutoCreateRowSorter(true);

		// (1) TODO: File-Open date from file and load into table
		// Use provided function: readDataFile() to open the file data
		
		// (2) TODO: Save the data from the table into the file. 
		// Use provided function: writeDataFile() to save the data into the file

		
		//(3)TODO: setup combobox


        //(4)TODO: Search button
		
		//(4)TODO: Show All button
		
        
		

		addPrediction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				predictions.add(new MyPrediction());
				//tm.fireTableRowsInserted(predictions.size(), predictions.size());
				tm.fireTableDataChanged();
			}    	  
		});

		removePrediction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int rowToDelete = myTable.getSelectedRow();
				if(rowToDelete>=0)
				{
					int result = JOptionPane.showConfirmDialog(SixNationsPredictionsGUI.this, "Are you sure you want to delete the selected row?");
					if (result == 0) {
						try {
							predictions.remove(rowToDelete);
							tm.fireTableRowsDeleted(rowToDelete, rowToDelete);
						}
						catch (IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(SixNationsPredictionsGUI.this, "Illegal Argument Exception\n " + ex.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else
					JOptionPane.showMessageDialog(SixNationsPredictionsGUI.this, "You must select a row to delete.");


			}    	  
		});
		

		// exits program from menu
		exitProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDown();
			}
		});

		// exits program by closing window
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				closeDown();
			}
		}); // end windowlistener
		
		// Adding menu bar and table to panel
	    topPane.add(teamComboBox);
	    topPane.add(btnSearch);
	    topPane.add(btnShowAll);
	    
	    btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (predictions.size() == 0) {
					String message = "There are no records in the table to search";
					JOptionPane.showMessageDialog(null, message);
				} else {
					 int selectedTeamIndex = teamComboBox.getSelectedIndex();
					 String teamToFilterBy = teams[selectedTeamIndex];
					 
					 ArrayList<MyPrediction> filteredPredictions = new ArrayList<MyPrediction>();
					 
					 for (int i = 0; i < predictions.size(); i++) {
						 if (predictions.get(i).getTeam().equals(teamToFilterBy)) {
							 filteredPredictions.add(predictions.get(i));
						 }
					 }
				     tm.data = filteredPredictions;
				     tm.fireTableDataChanged();
				}
			}
		});
	    btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (predictions.size() == 0) {
					String message = "There are no records in the table to search";
					JOptionPane.showMessageDialog(null, message);
				} else {
				     tm.data = predictions;
				     tm.fireTableDataChanged();
				}
			}
		});
	    
		this.setJMenuBar(myBar);
		this.add(topPane, BorderLayout.NORTH);
		this.add(myPane, BorderLayout.CENTER);
		this.setTitle("2019 Six Nations Rubgy");
		this.setVisible(true);
		this.pack();
	} // constructor

	private void createMenuBar() {
		fileLoad = new JMenuItem("Open");
		fileSaveAs = new JMenuItem("Save As");
		
		fileLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("File open clicked!");
				jfc.setCurrentDirectory(workingDirectory);
				int result = jfc.showOpenDialog(null);
				if (result == jfc.APPROVE_OPTION) {
					
					String message = "This will replace the existing data. Are you sure you want to do this?";
					int confirmResult = JOptionPane.showConfirmDialog(null, message, "Select an option", JOptionPane.YES_NO_CANCEL_OPTION);
					
					if (confirmResult == JOptionPane.YES_OPTION) {
						File file = jfc.getSelectedFile();
						try {
							readDataFile(file);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		fileSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("File save clicked!");
				jfc.setCurrentDirectory(workingDirectory);
				int result = jfc.showSaveDialog(null);
				if (result == jfc.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					try {
						writeDataFile(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		fileMenu = new JMenu("File");
		fileMenu.add(fileLoad);
		fileMenu.add(fileSaveAs);
		
		addPrediction = new JMenuItem("Add");
		removePrediction = new JMenuItem("Remove");
		
		recordMenu = new JMenu("Prediction");
		recordMenu.add(addPrediction);
		recordMenu.add(removePrediction);

		exitProgram = new JMenuItem("Exit Program");
		exitMenu = new JMenu("Exit");
		exitMenu.add(exitProgram);
		
		myBar = new JMenuBar();
		myBar.add(fileMenu);
		myBar.add(recordMenu);
		myBar.add(exitMenu);
	}

    
	public void writeDataFile(File f) throws IOException  {
		System.out.println(f.getAbsolutePath());
		
		FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(predictions);
        objectOut.close();
        
        int originalNumberOfPredictions = predictions.size();
        
        predictions = new ArrayList<MyPrediction>();
        tm.data = new ArrayList<MyPrediction>();
        tm.fireTableRowsDeleted(0, originalNumberOfPredictions);
	}

	public void readDataFile(File f) throws IOException, ClassNotFoundException {
		System.out.println(f.getAbsolutePath());
		
		FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Object obj = objectIn.readObject();
        objectIn.close();
        
        predictions = (ArrayList<MyPrediction>) obj;
        tm.data = predictions;
        tm.fireTableDataChanged();
	}
	

	

	// This behaviour will be used whether we close the application by clicking on the X button in the top-right corner or by selecting
	// an option from a menu, so it makes sense to have it as an independent method that can be reused.
	public void closeDown() {
			System.exit(0);
	}

	public static void main (String args[]){
		new SixNationsPredictionsGUI();
	} // main
} //class