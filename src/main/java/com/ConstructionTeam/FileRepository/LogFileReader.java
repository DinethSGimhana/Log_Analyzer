package com.ConstructionTeam.FileRepository;

import com.ConstructionTeam.DataModels.ErrorData;
import com.ConstructionTeam.DataModels.ErrorDataCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LogFileReader{
    public ArrayList<ErrorData> getData(String path, String previousAccessedDateTime,String lastAccessFilePath) throws IOException {
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList <ErrorData> errorDataList = new ArrayList<>();
        InputFileReader bufferFileReader = new BufferFileReader();
        BufferedReader bufferedReader = bufferFileReader.readFile(path);
        String line = bufferedReader.readLine();
                assert line != null;
                String lastDateTime = line.substring(0, line.indexOf(","));
                    while (line != null) {
                        if (previousAccessedDateTime !=null){
                            try {
                                if (line.contains("ERROR") && sdf.parse(lastDateTime).after(sdf.parse(previousAccessedDateTime))) {
                                    ErrorDataCollector errorDataCollector = new ErrorDataCollector();
                                    errorDataCollector.collectErrorData(errorDataList,line);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            if (line.contains("ERROR")) {
                                ErrorDataCollector errorDataCollector = new ErrorDataCollector();
                                errorDataCollector.collectErrorData(errorDataList,line);
                            }
                        }

                        line = bufferedReader.readLine();
                    }
                    LastAccessFileWriter lastAccessFileWriter = new LastAccessFileWriter();
                    lastAccessFileWriter.updateLastAccessDateTime(currentDateTime,lastAccessFilePath);
                    return errorDataList;
            }
        }
