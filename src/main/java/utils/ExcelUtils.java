package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {

    /**
     * Reads expected stations from the first column of the first sheet.
     * Throws an exception if file doesn't exist or is empty.
     */
    public static List<String> readExpectedStations(String filePath) throws IOException {
        List<String> stations = new ArrayList<>();
        // Load file from resources using ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IOException("File not found in resources: " + filePath);
        }

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    stations.add(cell.getStringCellValue().trim());
                }
            }
        }

        return stations;
    }


    /**
     * Writes actual station names to a new Excel file in the first column of a sheet called "ActualStations".
     * Always overwrites the file to avoid corruption.
     */
    public static void writeActualStations(String filePath, List<String> stationList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ActualStations");

        for (int i = 0; i < stationList.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(stationList.get(i));
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
