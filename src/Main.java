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

    private static BigInteger brent(BigInteger n) {
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return BigInteger.valueOf(2);
        Random rnd = new Random();
        BigInteger x = new BigInteger(n.bitLength()-1, rnd).add(BigInteger.ONE);
        BigInteger y = new BigInteger(n.bitLength()-1, rnd).add(BigInteger.ONE);
        BigInteger z = new BigInteger(n.bitLength()-1, rnd).add(BigInteger.ONE);
        BigInteger g = BigInteger.ONE;
        BigInteger c = BigInteger.ONE;
        BigInteger r = BigInteger.ONE;
        BigInteger x1 = BigInteger.ONE;
        BigInteger x2 = BigInteger.ONE;
        BigInteger i = BigInteger.ZERO;
        int stop = 0;

        while (g.equals(BigInteger.ONE)) {
            if (stop > 10) return n;
            stop++;
            x1 = x;
            while (i.compareTo(r)<0) {
                x = x.pow(2).mod(n.add(y)).mod(n);
                i = i.add(BigInteger.ONE);
            }
            BigInteger count = BigInteger.ZERO;
            while(count.compareTo(r)<0 && g.equals(BigInteger.ONE)) {
                x2 = x;
                i = BigInteger.ZERO;
                while (i.compareTo(z.min(r.subtract(count)))<0) {
                    x = x.pow(2).mod(n.add(y)).mod(n);
                    c = c.multiply(x.subtract(x1.subtract(x)).abs()).mod(n);
                    i = i.add(BigInteger.ONE);
                }
                g = n.gcd(c);
                count = count.add(z);
            }
            r = r.multiply(BigInteger.valueOf(2));
        }
        if (g.equals(n)) {
            while(true) {
                x2 = x2.pow(2).mod(n.add(y)).mod(n);
                g = n.gcd(x1.subtract(x2).abs());
                if (g.compareTo(BigInteger.ONE)>0) continue;
            }
        }
        return g;
    }

    private static boolean millerRabin(BigInteger n) {
        BigInteger two = BigInteger.valueOf(2);
        if (n.mod(two).equals(BigInteger.ZERO))
            return false;

        BigInteger s = n.subtract(BigInteger.ONE);
        while (s.mod(two).equals(BigInteger.ZERO))
            s = s.divide(two);

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            BigInteger nMinusOne = n.subtract(BigInteger.ONE);
            BigInteger r = new BigInteger(n.bitLength(), rand);
            BigInteger a = r.mod(nMinusOne).add(BigInteger.ONE);
            BigInteger temp = s;
            BigInteger m = a.modPow(temp, n); //(a ^ b) % c
            while (!temp.equals(nMinusOne) && !m.equals(BigInteger.ONE) && !m.equals(nMinusOne)) {
                BigInteger tmpMod = m;
                m = tmpMod.multiply(tmpMod).mod(n);//mulMod(m, m, n);
                temp.multiply(two);
            }
            if (!m.equals(nMinusOne) && temp.mod(two).equals(BigInteger.ZERO)) {
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