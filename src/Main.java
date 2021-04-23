import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int desired = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-data")) desired = i + 1;
        }
        String data = args[desired];
        String string = Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");
        int index_mode = string.indexOf("mode");
        String mode;
        if (index_mode == -1) {
            mode = "enc";
        } else {
            mode = string.substring(index_mode + 5, index_mode + 8);
        }
        int index_key = string.indexOf("key");
        int key;
        if (index_key == -1) {
            key = 0;
        } else {
            key = Integer.parseInt(string.substring(index_key + 4, index_key + 5));
        }
        int index_data = string.indexOf("data");
        if (index_data == -1) {
            data = "";
        }
        if (mode.equals("enc")) {
            System.out.println(encryption(data, key));
        } else if (mode.equals("dec")) {
            System.out.println(decryption(data, key));
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

    public static boolean isDigit(char c) {
        String s = String.valueOf(c);
        String digit = "1234567890";
        return digit.contains(s);
    }

    public static boolean isSym(char c) {
        String s = String.valueOf(c);
        String digit = "!. ?-_%#\"+=&`~\\";
        return digit.contains(s);
    }
}