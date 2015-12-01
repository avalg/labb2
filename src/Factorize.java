import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Factorize {
    static int[] apperances;
    static int counter;
    public static void main(String[] args) {
        // read in the number
        Scanner reader = new Scanner(System.in);
        String s = reader.next();
        while(!s.equals("0")) {
            apperances = new int[30];
            BigInteger n = new BigInteger(s);
            List<BigInteger> answer = primeFactor(n);
            counter = 0;

            if (answer != null) {
                for (int i = 0; i < answer.size(); i++) {
                    System.out.println(answer.get(i)+ "^" +apperances[i]);
                }
            }
        }
    }

    private static List<BigInteger> addToAnswer(BigInteger b, List<BigInteger> answer) {
        if(answer.contains(b)){
            int i = answer.indexOf(b);
            apperances[i]++;
        }else {
            answer.add(b);
            apperances[counter] = 1;
            counter++;
        }
        return answer;
    }

    private static List<BigInteger> primeFactor(BigInteger n){
        List<BigInteger> answer = new ArrayList<>();
        BigInteger two = BigInteger.valueOf(2);

        if(n.compareTo(two) < 0){
            return null;
        }

        while (n.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
            n = n.shiftRight(1);
            answer = addToAnswer(two, answer);
        }

        //left with odd values
        if (!n.equals(BigInteger.ONE)) {
            BigInteger b = BigInteger.valueOf(3);
            while (b.compareTo(n) < 0) {
                if (b.isProbablePrime(10)) {
                    BigInteger[] dr = n.divideAndRemainder(b);
                    if (dr[1].equals(BigInteger.ZERO)) {
                        answer = addToAnswer(b, answer);
                        n = dr[0];
                    }
                }
                b = b.add(two);
            }
            answer = addToAnswer(b, answer); //b will always be prime here...
        }

        return answer;
    }

}