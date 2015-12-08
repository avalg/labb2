import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by saraf on 2015-12-08.
 */
public class Primes1 {

    private static void primes(int n){
        ArrayList<Integer> list = new ArrayList<>();
        int i = 3;
        StringBuilder string = new StringBuilder();
        string.append("{ 2");
        int count = 1;
        while (count < n) {
            if (millerRabin(BigInteger.valueOf(i))) {
                string.append(", ");
                string.append(i);
                count++;
            }
            i = i+2;
        }
        string.append(" }");
        System.out.println(string);
    }

    private static boolean millerRabin(BigInteger n) {
        BigInteger two = BigInteger.valueOf(2);
        if (n.mod(two).equals(BigInteger.ZERO))
            return false;

        BigInteger s = n.subtract(BigInteger.ONE);
        while (s.mod(two).equals(BigInteger.ZERO))
            s = s.divide(two);

        Random rand = new Random();
        for (int i = 0; i < 14; i++) {
            BigInteger nMinusOne = n.subtract(BigInteger.ONE);
            BigInteger r = new BigInteger(n.bitLength(), rand);
            BigInteger a = r.mod(nMinusOne).add(BigInteger.ONE);
            BigInteger temp = s;
            BigInteger m = a.modPow(temp, n); //(a ^ b) % c
            while (!temp.equals(nMinusOne) && !m.equals(BigInteger.ONE) && !m.equals(nMinusOne)) {
                BigInteger tmpMod = m;
                m = tmpMod.multiply(tmpMod).mod(n);//mulMod(m, m, n);
                temp = temp.multiply(two);
            }
            if (!m.equals(nMinusOne) && temp.mod(two).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        primes(n);
    }
}
