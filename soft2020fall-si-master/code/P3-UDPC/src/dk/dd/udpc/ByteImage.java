package dk.dd.udpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ByteImage {

    public ByteImage(){


    }
    public byte[] imageToBytes(String fileName) throws IOException {


    File file = new File(fileName);

         byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);



        //read file into bytes[]
        fis.read(bytesArray);

        fis.close();

        return bytesArray;

}
}