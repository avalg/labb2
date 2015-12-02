import java.math.BigInteger;
import java.util.*;

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

        for (int i = 0; i<50; i++) {
            //left with odd values
            BigInteger result = two;
            result = pr(n);
            //TODO result is prime maybe yes? Check if prime yes?
            if (result.isProbablePrime(10)) {
                n = n.divide(result);
                int app = 1;
                while (n.mod(result).equals(BigInteger.ZERO)) {
                    app++;
                    n = n.divide(result);
                }
                answer.add(result);
                apperances[counter] = app;
                counter++;
            }
        }
        return answer;
    }

    private static BigInteger pr(BigInteger n) {
        long i = 1;
        Random rand = new Random();
        BigInteger x = new BigInteger(n.bitLength()-1, rand).add(BigInteger.ONE); //Subtract 1 to ensure x<n
        BigInteger y = x;
        long k = 2;
        long c = 0;
        for (int j = 0; j<10000; j++) { //TODO avbryt loopen! for the love of god!!
            i++;
            c++;
            x = x.pow(2).subtract(BigInteger.ONE).mod(n);
            BigInteger d = n.gcd(y.subtract(x));
            if (!d.equals(BigInteger.ONE)){
                //System.out.println(c);
                return d;
            }
            if (i == k) {
                y = x;
                k = k*2;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String s = "";
        while (!s.equals("0")) {
            // read the number
            System.out.println("");
            s = reader.next();
            apperances = new int[30];
            BigInteger n = new BigInteger(s);
            List<BigInteger> answer = primeFactor(n);
            counter = 0;

            if (answer != null) {
                Collections.sort(answer);
                for (int i = 0; i < answer.size(); i++) {
                    System.out.print(answer.get(i) + "^" + apperances[i] + " ");
                }
            }
        }
    }
}