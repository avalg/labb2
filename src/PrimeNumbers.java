import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by saraf on 2015-12-03.
 */
public class PrimeNumbers {
    ArrayList<BigInteger> list;

    private void generate() {
        list = new ArrayList<>();
        BigInteger counter = BigInteger.valueOf(3);
        while (list.size() < 500) {
            if (millerRabin(counter)) {
                list.add(counter);
            }
            counter = counter.add(BigInteger.valueOf(2));
        }
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

    public static void main(String[] args){
        PrimeNumbers prime = new PrimeNumbers();
        prime.generate();
    }
}
