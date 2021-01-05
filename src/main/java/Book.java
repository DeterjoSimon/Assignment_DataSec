import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Book {
    private Sheet sheet;
    private int head = 0;
    private final String path;
    private XSSFWorkbook workbook;

    public Book(String path) {
        this.path = path;
        connect(path);
    }

    public void connect(String path){
        //Parameters for Excel Database
        try {
            File file = new File(path);
            FileInputStream fip = new FileInputStream(file);

            this.workbook = new XSSFWorkbook(fip);
            this.sheet = workbook.getSheetAt(0);
        } catch (Exception e){
            System.err.println("No such file exists");
            e.printStackTrace();
        }

    }

    public Sheet getSheet(){
        return sheet;
    }

    public int getHead(){
        return head;
    }

    public void newUser(String name, String ID, String password) throws IOException {
        //Add an extra row
        Row row = sheet.createRow(++head);

        //Add name
        Cell cell = row.createCell(1);
        cell.setCellValue(name);
        //Add ID
        cell = row.createCell(2);
        cell.setCellValue(ID);
        //Add Password
        cell = row.createCell(3);
        cell.setCellValue(password);

        // Finally write on the sheet
        try (FileOutputStream outputStream = new FileOutputStream(path)){
            workbook.write(outputStream);
        }
    }

    public String getPassword(String username){
        String vUsername;
        String vPassword;
        int nrow = 0;
        try{
            //Iterate through every row
            while (nrow <= getHead()){
                Row row = getSheet().getRow(nrow);
                //Get username & password from that row
                Cell usernamecol = row.getCell(2);
                Cell passwordcol = row.getCell(3);
                vUsername = usernamecol.getStringCellValue();
                vPassword = passwordcol.getStringCellValue();
                if (username.equals(vUsername)){
                    return vPassword;
                }
                else {
                    nrow++;
                }
            }
            return "No such user exists";
        } catch (Exception e){
            e.printStackTrace();
            return "No such user exists";
        }
    }
    // getters and setters
}
