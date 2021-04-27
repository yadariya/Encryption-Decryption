import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        int desired_data = 0;
        int desired_in = 0;
        int desired_out = 0;
        int desired_mode = 0;
        int desired_key = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-data")) desired_data = i + 1;
            if (args[i].equals("-in")) desired_in = i + 1;
            if (args[i].equals("-out")) desired_out = i + 1;
            if (args[i].equals("-mode")) desired_mode = i + 1;
            if (args[i].equals("-key")) desired_key = i + 1;
        }
        String _data = args[desired_data];
        String file_in = args[desired_in];
        String file_out = args[desired_out];
        String mode = args[desired_mode];
        String _key = args[desired_key];
        int key = Integer.parseInt(_key);
        String data = "";
        if (desired_mode == 0) {
            mode = "enc";
        }
        if (desired_key == 0) {
            key = 0;
        }
        if (desired_data == 0 && desired_in == 0) {
            data = "";
        } else if (desired_data != 0 && desired_in == 0) {
            data = _data;
        } else if (desired_in != 0 && desired_data == 0) {
            File file = new File(file_in);
            Scanner scn = new Scanner(file);
            data = new String(Files.readAllBytes(Paths.get(file_in)));
            scn.close();
        }
        if (desired_out != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_out, false));
            if (mode.equals("enc")) {
                StringBuilder answer = encryption(data, key);
                writer.append(answer);
                writer.close();
            } else if (mode.equals("dec")) {
                StringBuilder answer = decryption(data, key);
                writer.append(answer);
                writer.close();
            }
        } else {
            if (mode.equals("enc")) {
                System.out.println(encryption(data, key));
            } else if (mode.equals("dec")) {
                System.out.println(decryption(data, key));
            }

        }
    }

    public static StringBuilder encryption(String s, int n) {
        StringBuilder newString = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int _new = (int) c + n;
            newString.append((char) _new);
        }
        return newString;
    }

    public static StringBuilder decryption(String s, int n) {
        StringBuilder newString = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int _new = (int) c - n;
            newString.append((char) _new);
        }
        return newString;
    }
}