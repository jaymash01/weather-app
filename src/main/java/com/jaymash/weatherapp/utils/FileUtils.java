package com.jaymash.weatherapp.utils;

import java.io.*;

public class FileUtils {

    public static String readFromInputStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean writeToFile(File file, String text) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(text.getBytes(), 0, text.length());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
