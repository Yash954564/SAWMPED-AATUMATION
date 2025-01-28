package com.framework.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides data from various file types such as JSON, XLSX and OTHER.
 */
public class DataProvider {

    /**
     * Reads data from a file based on the specified file type.
     *
     * @param filePath The path to the data file.
     * @param fileType The type of the data file (JSON, XLSX, or OTHER).
     * @return A list of maps representing the data.
     * @throws RuntimeException If the file type is not supported or if an error occurs while reading the data.
     */
    public static List<Map<String, String>> getData(String filePath, String fileType) {
        List<Map<String, String>> data = null; // Initialize the data variable
        try {
            LogManager.info("Reading data from the file " + filePath); // Log data loading
            switch (fileType.toUpperCase()) { // Switch based on file type (case-insensitive)
                case "JSON":
                    data = readJsonData(filePath); // Read data from JSON file
                    break;
                case "XLSX":
                    data = readExcelData(filePath); // Read data from XLSX file
                    break;
                case "OTHER":
                    data = readOtherData(filePath); // Read data from other file type
                    break;
                default:
                    LogManager.error("File type is not supported"); // Log the error message
                    throw new RuntimeException("File type is not supported"); // Throw exception for unsupported file type
            }
        } catch (Exception e) {
            LogManager.error("Error while reading the data from the file " + filePath + e.getMessage()); // Log the error message
            throw new RuntimeException("Error while reading the data from the file " + filePath + e.getMessage(), e);  // Throw exception if any error
        }
        return data; // Return the data
    }

    /**
     * Reads data from a JSON file.
     *
     * @param filePath The path to the JSON file.
     * @return A list of maps representing the data from the JSON file.
     * @throws IOException If an error occurs while reading the JSON data.
     */
    private static List<Map<String, String>> readJsonData(String filePath) throws IOException {
        LogManager.info("Reading the json data"); // Log message that json data is loading
        List<Map<String, String>> dataList = new ArrayList<>(); // List to hold all records from the file
        ObjectMapper mapper = new ObjectMapper(); // Initializing object mapper for json processing
        File file = new File(filePath); // Creating file object
        JsonNode rootNode = mapper.readTree(file); // Reading json data from the file

        // Logic for when the root node is an array of objects
        if (rootNode.isArray()) {
            for (JsonNode jsonNode : rootNode) {
                if (jsonNode.isObject()) {
                    Map<String, String> data = new HashMap<>(); // Map to hold individual record
                    ObjectNode objectNode = (ObjectNode) jsonNode; // convert node into object node for reading data
                    Iterator<String> keys = objectNode.fieldNames();  // reading all keys from object node
                    while (keys.hasNext()) {
                        String key = keys.next(); // reading the key
                        String value = objectNode.get(key).asText(); // reading the value against the key
                        data.put(key, value); // adding each key and value in map
                    }
                    dataList.add(data); // adding the record to the list of records
                }
            }
        } else if (rootNode.isObject()) { // Logic when root node is a simple object
            Map<String, String> data = new HashMap<>();  // Map to hold individual record
            ObjectNode objectNode = (ObjectNode) rootNode; // Convert node into object node
            Iterator<String> keys = objectNode.fieldNames();  // Reading keys from the object node
            while (keys.hasNext()) {
                String key = keys.next(); // Reading the key
                String value = objectNode.get(key).asText(); // reading the value of a key
                data.put(key, value); // adding key and value to the record
            }
            dataList.add(data); // add record to the list of record.
        }
        return dataList; // returning the list of data read from json file
    }

    /**
     * Reads data from an Excel (XLSX) file.
     *
     * @param filePath The path to the XLSX file.
     * @return A list of maps representing the data from the XLSX file.
     * @throws IOException If an error occurs while reading the Excel data.
     */
    private static List<Map<String, String>> readExcelData(String filePath) throws IOException {
        LogManager.info("Reading data from excel"); // Log that data reading has started from excel file
        List<Map<String, String>> dataList = new ArrayList<>(); //  List to hold all records from the file
        FileInputStream file = new FileInputStream(new File(filePath)); // Reading file as an input stream
        Workbook workbook = new XSSFWorkbook(file); // Reading excel workbook
        Sheet sheet = workbook.getSheetAt(0); // Reading first sheet from the workbook
        List<String> headers = new ArrayList<>(); // List to hold headers for each column in the file.

        Iterator<Row> rowIterator = sheet.iterator(); // creating a row iterator
        if (rowIterator.hasNext()) { // check if there are records in the file
            Row headerRow = rowIterator.next(); // read header row from the sheet
            Iterator<Cell> headerCellIterator = headerRow.cellIterator(); //  Reading cells from the header row
            while (headerCellIterator.hasNext()) {
                Cell cell = headerCellIterator.next();  // Reading cell data from the header cell
                headers.add(cell.getStringCellValue()); // add each header to the list of header
            }
        }
        while (rowIterator.hasNext()) { // iterate each record from excel file
            Row row = rowIterator.next(); // read each row of data
            Iterator<Cell> cellIterator = row.cellIterator(); // iterator for all the cells in the row
            Map<String, String> rowData = new HashMap<>(); //  Map to hold individual record
            int columnIndex = 0; // Column index for reading record
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); // Reading cell data from row
                if (cell.getCellType().toString().equalsIgnoreCase("STRING")) {
                    rowData.put(headers.get(columnIndex), cell.getStringCellValue()); // adding each key value pair to the record
                } else if (cell.getCellType().toString().equalsIgnoreCase("NUMERIC")) {
                    rowData.put(headers.get(columnIndex), String.valueOf(cell.getNumericCellValue()));  // adding each key value pair to the record
                }
                columnIndex++; // Increment index to read next column data
            }
            dataList.add(rowData); // Add record to list
        }
        workbook.close(); // Close the workbook
        return dataList; // Return list of records
    }

    /**
     * Reads data from other file types (e.g., text files).
     *
     * @param filePath The path to the file.
     * @return A list of maps representing the data, where each map contains a single entry
     *         with the key "data" and the line from the file as the value.
     * @throws IOException If an error occurs while reading the file.
     */
    private static List<Map<String, String>> readOtherData(String filePath) throws IOException {
        LogManager.info("Reading data from other file type");  // Log message that loading from other file is started
        List<Map<String, String>> dataList = new ArrayList<>(); // List to hold all records
        List<String> lines = Files.readAllLines(Paths.get(filePath)); // Read all the records from file
        for (String line : lines) {
            Map<String, String> dataMap = new HashMap<>(); // creating record object
            dataMap.put("data", line); // Adding data to record object with key "data"
            dataList.add(dataMap); // Adding record to the list
        }
        return dataList; // return list of records.
    }
}