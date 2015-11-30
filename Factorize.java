import java.math.BigInteger;
import java.io.*;

public class Factorize {
    int[] apperances;
    int counter;
    public static void main(String[] args) {
        // read in the number
        Scanner reader = new Scanner(System.in);
        String s = reader.next();
        while(s != "0") {
            apperances = new int[30];
            BigInteger n = s;
            List<BigInteger> answer = primeFactor(n);
            counter = 0;

            for (int i = 0; i < answer.size(); i++) {
                System.out.println(answer[i]+ "^" +apperances[i]);
            }
        }
    }

    private List<BigInteger> primeFactor(BigInteger n){
        List<BigInteger> answer = new ArrayList<BigInteger>();
        BigInteger two = BigInteger.valueOf(2);

        if(n.compareTo(2) < 0){
            return null;
        }

        while (n.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
            n = n.shiftRight(1);
            if(answer.contains(two)){
                int i = answer.indexOf(two);
                apperances[i]++;
            }else {
                answer.add(two);
                apperances[counter] = 1;
                counter++;
            }
        }

        //left with odd values
        if (!a.equals(BigInteger.ONE)) {
            BigInteger b = BigInteger.valueOf(3);
            while (b.compareTo(a) < 0) {
                if (b.isProbablePrime(10)) {
                    BigInteger[] dr = a.divideAndRemainder(b);
                    if (dr[1].equals(BigInteger.ZERO)) {
                        result.add(b);
                        a = dr[0];
                    }
                }
                b = b.add(two);
            }
            result.add(b); //b will always be prime here...
        }




        return answer;
    }

}