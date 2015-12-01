import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int[] apperances;
    static int counter;
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

    private static BigInteger pr(BigInteger n) {
        long i = 1;
        Random rand = new Random();
        BigInteger x = new BigInteger(n.bitLength()-1, rand).add(BigInteger.ONE); //Subtract 1 to ensure x<n
        BigInteger y = x;
        long k = 2;

        while(true) { //TODO avbryt loopen! for the love of god!!
            i++;
            x = x.pow(2).subtract(BigInteger.ONE).mod(n);
            BigInteger d = n.gcd(y.subtract(x));
            if (!d.equals(BigInteger.ONE) && !d.equals(n)){
                return d;
            }
            if (i == k) {
                y = x;
                k = k*2;
            }
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String s = "";
        while (!s.equals("0")) {
            // read the number
            s = reader.next();
            apperances = new int[30];
            BigInteger n = new BigInteger(s);
            List<BigInteger> answer = primeFactor(n);
            counter = 0;

            if (answer != null) {
                for (int i = 0; i < answer.size(); i++) {
                    System.out.print(answer.get(i) + "^" + apperances[i] + " ");
                }
                System.out.println("");
            }
        }
    }
}