package correcter;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileInputStream input;
        FileOutputStream output;

        switch (scanner.next()) {
            case ("encode"): {
                input = new FileInputStream("send.txt");                    // Кодирует
                output = new FileOutputStream("encoded.txt", false);
                doEncodeHamming(input, output);
                break;
            }
            case ("send"): {
                input = new FileInputStream("encoded.txt");                 // Имитирует отправку сообщения
                output = new FileOutputStream("received.txt", false);
                doSend(input, output);
                break;
            }
            case ("decode"): {
                input = new FileInputStream("received.txt");                // Декадирует сообщение
                output = new FileOutputStream("decoded.txt", false);
                doDecodeHamming(input, output);
                break;
            }
            default: {
                input = new FileInputStream("send.txt");                  
                output = new FileOutputStream("received.txt");
            }
        }



        input.close();
        output.close();
    }

    public static void doDecodeHamming(FileInputStream input, FileOutputStream output) throws IOException {
        byte a = (byte) input.read();
        boolean[] bufer = new boolean[8];
        boolean[] checkBit = new boolean[3];
        int indexError;
        int count = 0;
        StringBuilder str = null;


        while (a != -1) {
            if(count % 2 == 0) {
                str = new StringBuilder();
            }

            for (int i = 7; i >= 0; i--) {
                bufer[7 - i] = (a >> i & 1) == 1;
            }
            checkBit[0] = bufer[2]^bufer[4]^bufer[6] == bufer[0];
            checkBit[1] = bufer[2]^bufer[5]^bufer[6] == bufer[1];
            checkBit[2] = bufer[4]^bufer[5]^bufer[6] == bufer[3];
            indexError = (checkBit[0] ? 0 : 1) + (checkBit[1] ? 0 : 2) + (checkBit[2] ? 0 : 4) - 1;
            if (indexError == -1) {
                bufer[7] = !bufer[7];
            } else {
                bufer[indexError] = !bufer[indexError];
            }

            str.append(bufer[2] ? "1" : "0").append(bufer[4] ? "1" : "0").append(bufer[5] ? "1" : "0").append(bufer[6] ? "1" : "0");

            if(count % 2 == 1) {
                output.write((byte) Integer.parseInt(str.toString(), 2));
            }


            a = (byte) input.read();
            count++;
        }
    }

    public static void doEncodeHamming(FileInputStream input, FileOutputStream output) throws IOException {
        boolean[] bufer = new boolean[4];
        byte a = (byte) input.read();
        StringBuilder str;
        while (a != -1) {
            for (int i = 7; i >= 0; i--) {
                bufer[3 - i % 4] = (a >> i & 1) == 1;
                if (i % 4 == 0) {
                    str = new StringBuilder();
                    str.append(bufer[0]^bufer[1]^bufer[3] ? "1" : "0")                                          // 0 - 3
                            .append(bufer[0]^bufer[2]^bufer[3] ? "1" : "0")
                            .append(bufer[0] ? "1" : "0")
                            .append(bufer[1]^bufer[2]^bufer[3] ? "1" : "0")

                            .append(bufer[1] ? "1" : "0")                                                       // 4 - 7
                            .append(bufer[2] ? "1" : "0")
                            .append(bufer[3] ? "1" : "0").append("0");
                    System.out.println(str);
                    output.write((byte) Integer.parseInt(str.toString(), 2));
                }
            }
            a = (byte) input.read();
        }


    }

    public static void doSend(FileInputStream input, FileOutputStream output) throws IOException {
        byte a;
        Random random = new Random();
        int i = 0;
        while(input.available() > 0) {
            a = (byte) input.read();
            a ^= 1 << random.nextInt(8);
            output.write(a);
            System.out.println(i++);
        }
    }

}
