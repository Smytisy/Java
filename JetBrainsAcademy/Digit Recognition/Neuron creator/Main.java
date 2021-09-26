package recognition;

import java.util.*;


public class Main {

    private static Neuron neon = new Neuron();

    public static void main(String[] args) {
        neon.setWeightNeuron();
        readTable();
        System.out.println("This number is " + neon.findNumber_1_or_0());
    }


    static void readTable() {
        System.out.println("Input grid:");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            String str = scanner.nextLine();
            for (int j = 0; j < str.length(); j++) {
                neon.set_A(i * 3 + j, str.charAt(j) == 'x' || str.charAt(j) == 'X' ? 1 : 0);
                System.out.println(str.charAt(j) == 'x' ? 1 : 0);
            }
        }
    }

}

