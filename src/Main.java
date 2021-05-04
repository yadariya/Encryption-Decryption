import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

interface Algorithm {
    StringBuilder perform(String data, int key);

    static boolean isSym(char c) {
        String s = String.valueOf(c);
        String digit = "!. ?-_";
        return digit.contains(s);
    }

    static boolean isDigit(char c) {
        String s = String.valueOf(c);
        String digit = "1234567890";
        return digit.contains(s);
    }
}

class EncryptionWithShift implements Algorithm {

    @Override
    public StringBuilder perform(String data, int key) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Algorithm.isDigit(data.charAt(i)) || Algorithm.isSym(data.charAt(i))) {
                newString.append(data.charAt(i));
            } else if ((int) c >= 97 && (int) c <= 122) {
                int dif = (int) c - 96 + key;
                int _new = dif % 26 + 96;
                newString.append((char) _new);
            } else {
                int dif = (int) c - 65 + key;
                int _new = dif % 26 + 65;
                newString.append((char) _new);
            }
        }
        return newString;
    }
}

class DecryptionWithShift implements Algorithm {

    @Override
    public StringBuilder perform(String data, int key) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Algorithm.isDigit(data.charAt(i)) || Algorithm.isSym(data.charAt(i))) {
                newString.append(data.charAt(i));
            } else if ((int) c >= 97 && (int) c <= 122) {
                int dif = (int) c - 96 - key;
                int _new;
                if (dif > 0) {
                    _new = dif % 26 + 96;
                } else {
                    _new = (int) c - key + 26;
                }
                newString.append((char) _new);
            } else {
                int dif = (int) c - 65 - key;
                int _new;
                if (dif > 0) {
                    _new = dif % 26 + 65;
                } else {
                    _new = (int) c - key + 26;
                }
                newString.append((char) _new);
            }
        }
        return newString;
    }
}

class EncryptionUnicode implements Algorithm {
    @Override
    public StringBuilder perform(String data, int key) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int _new = (int) c + key;
            newString.append((char) _new);
        }
        return newString;
    }
}

class DecryptionUnicode implements Algorithm {
    @Override
    public StringBuilder perform(String data, int key) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int _new = (int) c - key;
            newString.append((char) _new);
        }
        return newString;
    }
}


class Main {
    public static void main(String[] args) throws IOException {
        int desired_data = 0;
        int desired_in = 0;
        int desired_out = 0;
        int desired_mode = 0;
        int desired_key = 0;
        int desired_alg = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-data")) desired_data = i + 1;
            if (args[i].equals("-in")) desired_in = i + 1;
            if (args[i].equals("-out")) desired_out = i + 1;
            if (args[i].equals("-mode")) desired_mode = i + 1;
            if (args[i].equals("-key")) desired_key = i + 1;
            if (args[i].equals("-alg")) desired_alg = i + 1;
        }
        String _data = args[desired_data];
        String file_in = args[desired_in];
        String file_out = args[desired_out];
        String mode = args[desired_mode];
        String _key = args[desired_key];
        String alg = args[desired_alg];
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
        } else if (desired_data == 0) {
            File file = new File(file_in);
            Scanner scn = new Scanner(file);
            data = new String(Files.readAllBytes(Paths.get(file_in)));
            scn.close();
        }
        if (desired_out != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_out, false));
            if (mode.equals("enc") && alg.equals("shift")) {
                EncryptionWithShift encryption = new EncryptionWithShift();
                StringBuilder answer = encryption.perform(data, key);
                writer.append(answer);
                writer.close();
            } else if (mode.equals("enc") && alg.equals("unicode")) {
                EncryptionUnicode encryption = new EncryptionUnicode();
                StringBuilder answer = encryption.perform(data, key);
                writer.append(answer);
                writer.close();
            } else if (mode.equals("dec") && alg.equals("shift")) {
                DecryptionWithShift decryption = new DecryptionWithShift();
                StringBuilder answer = decryption.perform(data, key);
                writer.append(answer);
                writer.close();
            } else {
                DecryptionUnicode decryption = new DecryptionUnicode();
                StringBuilder answer = decryption.perform(data, key);
                writer.append(answer);
                writer.close();
            }
        } else {
            if (mode.equals("enc") && alg.equals("shift")) {
                EncryptionWithShift encryption = new EncryptionWithShift();
                System.out.println(encryption.perform(data, key));

            } else if (mode.equals("enc") && alg.equals("unicode")) {
                EncryptionUnicode encryption = new EncryptionUnicode();
                System.out.println(encryption.perform(data, key));

            } else if (mode.equals("dec") && alg.equals("shift")) {
                DecryptionWithShift decryption = new DecryptionWithShift();
                System.out.println(decryption.perform(data, key));
            } else {
                DecryptionUnicode decryption = new DecryptionUnicode();
                System.out.println(decryption.perform(data, key));
            }
        }
    }
}