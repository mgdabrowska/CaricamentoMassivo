package pl.gosia.CaricamentoMassivoMaven;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.FileWriter;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class Controler {
	private Desktop desktop = Desktop.getDesktop();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private static String FILE_NAME = "";
	private LinkedHashSet<String> set;

	Validator[] validators = new Validator[]{
			this::validateCell1,
			this::validateCell1,
			this::validateCell1,
	};

	@FXML
	private void validate(KeyEvent event1) {
		Main majority = new Main();
		set = new LinkedHashSet<String>();
		for (Node node : anchorPane.getChildren()) {

			if (node instanceof TextField && ((TextField) node).getText().length() == 1
					&& ((TextField) node).getText().matches("[a-n]|[0]")) {
				if (!set.contains(((TextField) node).getText()) || (((TextField) node).getText().matches("[0]"))) {

					set.add(((TextField) node).getText());
					System.out.println(set + " set");

				} else {
					((TextField) node).setText("");

				}
				System.out.println(((TextField) node).getText());
			} else if (node instanceof TextField && ((TextField) node).getText().length() >= 1
					&& !((TextField) node).getText().matches("[a-n]|[0]")) {
				majority.msgBox(null);
				((TextField) node).setText("");
			}

		}
		System.out.println(set + " glowny zbior");
	}

	@FXML
	public void locateFile(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		Window ownerWindow = null;
		configureFileChooser(chooser);
		File file = chooser.showOpenDialog(ownerWindow);

		if (file != null) {
			openFile(file);
			FILE_NAME = file.getPath();

		}

	}

	private static void configureFileChooser(final FileChooser fileChooser) {

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Text", "*.*"),
				new FileChooser.ExtensionFilter("Excel", "*.xls"), new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void readFile() {

		Writer writer = null;
		try {

			System.out.println(FILE_NAME);
			InputStream file = new BufferedInputStream(new FileInputStream(new File(FILE_NAME)));

			Workbook workbook = new XSSFWorkbook(file);
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			File nota = new File("D:/write.txt");
			writer = new BufferedWriter(new FileWriter(nota));

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() >= 1) {
					Iterator<Cell> cellIterator = row.iterator();
					// writer.write("");

					while (cellIterator.hasNext()) {
						Cell cell = null;
						int j =0;
						for (String value: set) {
							j++;
						int i = CellReference.convertColStringToIndex(value);
System.out.println(j+ " coś tam " + i);
							cell = row.getCell(i);
							boolean apply = validators[i].apply(cell);
							if(cell.getStringCellValue().length()==5){
							System.out.println("kolumn index ");
							}
						if (cell.getCellTypeEnum() == CellType.STRING) {
							System.out.print(cell.getStringCellValue() + "\t");

							writer.write("" + String.valueOf(cell.getStringCellValue()));
						} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							System.out.print(cell.getNumericCellValue() + "\t");
							writer.write("" + String.valueOf(cell.getNumericCellValue()));
						}
						writer.write("*");


						}
						writer.write("\r\n");
						break;
					}
					System.out.println("");
				}
				file.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	//nowa metoda z validacją 
	public boolean validateCell1(Cell cell){

			if(cell.getStringCellValue().length()==5 && (cell.getCellTypeEnum()== CellType.STRING || cell.getCellTypeEnum() == CellType.NUMERIC))
					{
				System.out.println("POPRAWNA WARTOŚ");
				return true;
				}
		return false;
				
			

	}

	interface Validator extends Function<Cell, Boolean> {

	}
}
