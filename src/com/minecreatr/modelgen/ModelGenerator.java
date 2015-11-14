package com.minecreatr.modelgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class that does all the actual work
 */
public class ModelGenerator {




    /**
     * Generates the model resourcepack
     * @param file The File
     * @param n Amount of frames in the video
     * @param f The framerate
     */
    public static float generateModelRP(File file, int n, int f){
        try {
            ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(file));
            float c = -1/((float)n/360/(float)f*20);
            String compassJson = "{\"parent\": \"item/image\",\"textures\": {\"layer0\": \"items/compass_16\"},\"overrides\": [";
            for (int i = 0 ; i < n ; i++){
                float a = (float)i/(float)n;
                //System.out.println("n: " + n + " i: " + i + " a: " + a);
                String b = pad(i, n);
                String pred = "{ \"predicate\": { \"angle\": " + a + " }, \"model\": \"vp/" + b + "\" }";
                if (i != (n-1)){
                    pred = pred + ",";
                }
                compassJson = compassJson + pred;

                String modelJson = "{\n" +
                        "    \"parent\": \"item/image\",\n" +
                        "    \"textures\": {\n" +
                        "        \"layer0\": \"vp/" + b + "\"\n" +
                        "    }\n" +
                        "}";
                ZipEntry entry = new ZipEntry("assets\\minecraft\\models\\vp\\" + b + ".json");
                outStream.putNextEntry(entry);
                byte[] data = modelJson.getBytes();
                outStream.write(data, 0, data.length);
                outStream.closeEntry();
            }
            compassJson = compassJson + "]}";
            ZipEntry compassEntry = new ZipEntry("assets\\minecraft\\models\\item\\compass.json");
            outStream.putNextEntry(compassEntry);
            byte[] data = compassJson.getBytes();
            outStream.write(data, 0, data.length);
            outStream.close();
            return c;
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

    }


    private static String pad(int num, int max){
        int maxLen = String.valueOf(max).length();
        int curLen = String.valueOf(num).length();
        if (maxLen > curLen){
            String toPad = "";
            for (int i = curLen ; i < maxLen ; i++){
                toPad = toPad + "0";
            }
            return toPad + num;
        }
        else {
            return String.valueOf(num);
        }
    }
}
