package com.perficient.hr.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteFileUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(WriteFileUtils.class);

	/*private static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }*/
    
	/**
     * write input stream to file server
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
	public static String writeToFileServer(InputStream inputStream, String location) throws IOException {
        try(FileOutputStream outputStream = new FileOutputStream(new File(location));) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException ioe) {
            logger.error("Unable to import file "+location+" IO Exception is: "+ioe);
        }
        return  location;
    }
	
	public static String writeToFileServer(InputStream inputStream, String location, String fileName) throws IOException {
		File file = new File(location);
		//check if folder is present
		if(!file.exists()){
			file.mkdirs();
		}
		//check if file is present
		file = new File(location+"\\"+fileName);
		if(file.exists()){
			fileName = getFileName(location, fileName);
		}
		writeToFileServer(inputStream, location+"\\"+fileName);
		return fileName;
	}
	
	private static String getFileName(String location, String fileName){
		String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if(fileName.contains("_")){
			String name = fileName.substring(0, fileName.indexOf("_"));
			String val = fileName.substring(fileName.indexOf("_")+1, fileName.indexOf("."));
			try{
				fileName = name +"_"+ ((Integer.parseInt(val)+1))+ext;
			} catch (NumberFormatException e) {
				name = fileName.substring(0, fileName.indexOf("."));
				fileName = name+"_1"+ext;
			}
		} else {
			String name = fileName.substring(0, fileName.indexOf("."));
			fileName = name+"_1"+ext;
		}
		File file = new File(location+"\\"+fileName);
		if(file.exists()){
			return getFileName(location, fileName);
		} else {
			return fileName;	
		}
	}
}
