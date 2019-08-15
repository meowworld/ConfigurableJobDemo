package com.example.demo.web;


import jdk.internal.util.xml.impl.ReaderUTF8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


@Slf4j
@RestController
@RequestMapping("/txt")
public class TxtController {

    /**
     * 读取一个txt文件，按照行读取。包括空格和换行
     */

    @GetMapping("/test")
    public String readTxt(){

        InputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("mailBanner/mail.txt");
            streamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(streamReader);

            String str = "";
            while ((str=reader.readLine()) != null){
                stringBuffer.append(str);
            }
            log.error(stringBuffer.toString());

        } catch (IOException e) {
            log.error("========读取文件异常========");
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (streamReader!=null)
                    streamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string = stringBuffer.toString();
        System.out.println(string);


        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        String property = System.getProperty("line.separator");

        System.out.println(property);
    }



}
