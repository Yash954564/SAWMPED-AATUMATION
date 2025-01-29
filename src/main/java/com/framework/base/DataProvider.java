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

public class DataProvider {

    public static  List<Map<String, String>> getData(String filePath, String fileType){
        List<Map<String, String>> data = null;
        try {
            LogManager.info("Reading data from the file "+filePath);
            switch (fileType.toUpperCase()){
                case "JSON":
                    data = readJsonData(filePath);
                    break;
                case "XLSX":
                    data = readExcelData(filePath);
                    break;
                case "OTHER":
                    data= readOtherData(filePath);
                    break;
                default:
                    LogManager.error("File type is not supported");
                    throw new RuntimeException("File type is not supported");
            }
        }catch (Exception e){
            LogManager.error("Error while reading the data from the file "+filePath+e.getMessage());
            throw new RuntimeException("Error while reading the data from the file "+filePath+e.getMessage());
        }
        return data;
    }

    // Method to read JSON data
    private static List<Map<String, String>> readJsonData(String filePath) throws IOException {
        LogManager.info("Reading the json data");
        List<Map<String, String>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        JsonNode rootNode = mapper.readTree(file);

        if(rootNode.isArray()){
            for(JsonNode jsonNode: rootNode){
                if(jsonNode.isObject()){
                    Map<String, String> data = new HashMap<>();
                    ObjectNode objectNode = (ObjectNode)jsonNode;
                    Iterator<String> keys = objectNode.fieldNames();
                    while(keys.hasNext()){
                        String key = keys.next();
                        String value = objectNode.get(key).asText();
                        data.put(key,value);
                    }
                    dataList.add(data);
                }
            }
        }else if (rootNode.isObject()){
            Map<String, String> data = new HashMap<>();
            ObjectNode objectNode = (ObjectNode)rootNode;
            Iterator<String> keys = objectNode.fieldNames();
            while(keys.hasNext()){
                String key = keys.next();
                String value = objectNode.get(key).asText();
                data.put(key,value);
            }
            dataList.add(data);
        }
        return dataList;
    }
    // Method to read excel data
    private static  List<Map<String, String>> readExcelData(String filePath) throws IOException {
        LogManager.info("Reading data from excel");
        List<Map<String, String>> dataList = new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<String> headers = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()){
            Row headerRow = rowIterator.next();
            Iterator<Cell> headerCellIterator = headerRow.cellIterator();
            while(headerCellIterator.hasNext()){
                Cell cell = headerCellIterator.next();
                headers.add(cell.getStringCellValue());
            }
        }
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Map<String, String> rowData = new HashMap<>();
            int columnIndex =0;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(cell.getCellType().toString().equalsIgnoreCase("STRING")) {
                    rowData.put(headers.get(columnIndex), cell.getStringCellValue());
                } else if(cell.getCellType().toString().equalsIgnoreCase("NUMERIC")) {
                    rowData.put(headers.get(columnIndex), String.valueOf(cell.getNumericCellValue()));
                }
                columnIndex++;
            }
            dataList.add(rowData);
        }
        workbook.close();
        return dataList;
    }

    private static  List<Map<String, String>> readOtherData(String filePath) throws IOException {
        LogManager.info("Reading data from other file type");
        List<Map<String,String>> dataList= new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for(String line:lines){
            Map<String, String> dataMap= new HashMap<>();
            dataMap.put("data", line);
            dataList.add(dataMap);
        }
        return dataList;
    }

}