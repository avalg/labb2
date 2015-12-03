import java.math.BigInteger;
import java.util.*;

public class Main {
    static HashMap<BigInteger, Integer> apperances;

    private static List<BigInteger> addToAnswer(BigInteger b, List<BigInteger> answer) {
        if(answer.contains(b)){
            apperances.put(b, apperances.get(b)+1);
        }else {
            answer.add(b);
            apperances.put(b, 1);
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

        int counter = 0;
        while (n.compareTo(BigInteger.ONE)>0) {
            //left with odd values
            BigInteger result = pr(n);
            //TODO result is prime maybe yes? Check if prime yes?
            if (millerRabin(result)) {
                n = n.divide(result);
                int app = 1;
                while (n.mod(result).equals(BigInteger.ZERO)) {
                    app++;
                    n = n.divide(result);
                }
                answer.add(result);
                apperances.put(result, app);
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
        for (int j = 0; j<10000; j++) { //TODO avbryt loopen! for the love of god!!
            i++;
            x = x.pow(2).subtract(BigInteger.ONE).mod(n);
            BigInteger d = n.gcd(y.subtract(x));
            if (!d.equals(BigInteger.ONE)){
                return d;
            }
            if (i == k) {
                y = x;
                k = k*2;
            }
        }
        return n;
    }

    private static boolean millerRabin(BigInteger n){
        Random rnd = new Random();
        int counter = 0;
        for(int i = 0; i < 20; i++) {
            BigInteger a = new BigInteger(n.bitLength(), rnd);
            while (a.equals(BigInteger.ZERO)) {
                a = new BigInteger(n.bitLength(), rnd);
            }
            BigInteger nMinus = n.subtract(BigInteger.ONE);
            int s = nMinus.getLowestSetBit();

            a.modPow(nMinus.shiftRight(s), n);
            if (a.equals(BigInteger.ONE)) {
                break;
            }
            for (int j = 0; j < s - 1; j++) {
                if (a.equals(nMinus)) {
                    break;
                }
                a = a.multiply(a).mod(n);
            }
            if (a.equals(nMinus)){
                break;
            }
            counter++;
            if(counter > 10){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String s = "";
        while (!s.equals("0")) {
            // read the number
            System.out.println("");
            s = reader.next();
            apperances = new HashMap<>();
            BigInteger n = new BigInteger(s);
            List<BigInteger> answer = primeFactor(n);
            if (answer != null) {
                Collections.sort(answer);
                for (BigInteger prime : answer) {
                    System.out.print(prime + "^" + apperances.get(prime) + " ");
                }
            }
        }
    }
}