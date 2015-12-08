import java.math.BigInteger;
import java.util.*;

public class Main {
    static HashMap<BigInteger, Integer> apperances;
    private static final int[] PRIMES = {
            3,   5,   7,  11,  13,  17,  19,  23,  29,  31,  37,  41,  43,  47,  53,  59,
            61,  67,  71,  73,  79,  83,  89,  97, 101, 103, 107, 109, 113, 127, 131, 137,
            139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227,
            229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
            317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419,
            421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509,
            521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617,
            619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727};

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
        for(int i = 0; i < 127; i++){
            if(n.mod(BigInteger.valueOf(PRIMES[i])).equals(BigInteger.ZERO)){
                BigInteger r = BigInteger.valueOf(PRIMES[i]);
                n = n.divide(r);
                int app = 1;
                while (n.mod(r).equals(BigInteger.ZERO)) {
                    app++;
                    n = n.divide(r);
                }
                answer.add(r);
                apperances.put(r, app);
            }
        }
        while (n.compareTo(BigInteger.ONE)>0) {
            //left with odd values
            BigInteger result = brent(n);
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
        System.out.println("entered pr");
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
            if (stop >= 10) return n;
            stop++;
            x1 = x;
            while (i.compareTo(r)<0) {
                x = x.modPow(BigInteger.valueOf(2), n).add(y).mod(n);
                i = i.add(BigInteger.ONE);
            }
            BigInteger count = BigInteger.ZERO;
            while(count.compareTo(r)<0 && g.equals(BigInteger.ONE)) {
                x2 = x;
                i = BigInteger.ZERO;
                while (i.compareTo(z.min(r.subtract(count)))<0) {
                    x = x.modPow(BigInteger.valueOf(2), n.add(y)).mod(n);
                    c = c.multiply(x.subtract(x1).abs()).mod(n);
                    i = i.add(BigInteger.ONE);
                }
                g = n.gcd(c);
                count = count.add(z);
            }
            r = r.multiply(BigInteger.valueOf(2));
        }
        if (g.equals(n)) {
            for (int j = 0; j<10; j++) {
                x2 = x2.modPow(BigInteger.valueOf(2), n).add(y).mod(n);
                g = n.gcd(x1.subtract(x2).abs());
                if (g.compareTo(BigInteger.ONE)>0) break;
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

    /*private static BigInteger brent2(BigInteger n) {
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = x;
        BigInteger g = BigInteger.ONE;
        int r =
    }*/

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