package springbox.exceptionhandle.recover;

import java.util.Arrays;
import java.util.List;

public class calcMoney {
    private static final String NOT_EXIST_FILE_PATH = "NOT_EXIST_FILE_PATH";

    public static void main(String[] args) {
        List<Integer> spendMoneys = Arrays.asList(1000, 5000, 10000, null, 50000);

        int index = 0;
        int total = 0;

        while (index < spendMoneys.size()) {
            try {
                total = total + spendMoneys.get(index);
                index++;
            } catch (NullPointerException e) {
                System.out.println("예외 발생! 다시 처음부터.");
                spendMoneys.set(index, 0);
                index = 0;
                total = 0;
            }
        }

        System.out.println("total = " + total);
    }
}
