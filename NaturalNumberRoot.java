import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Jamal Aden
 *
 */
public final class NaturalNumberRoot {
    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is not null";
        assert r >= 2 : "Violation of: r >= 2";

        if (!n.isZero()) {
            //makes sure its not zero//
            NaturalNumber low = new NaturalNumber2();
            NaturalNumber high = new NaturalNumber2(n);
            high.increment(); // Sets the high/ upper bound of range to n+1 to help begin the binary search

            while (low.compareTo(high) < 0) { // Makes sure low is less than high before continuing
                NaturalNumber mid = new NaturalNumber2(low);
                mid.add(high);
                mid.divide(new NaturalNumber2(2)); // sets mid = to the midpoint (low + high) / 2 for binary search purposes

                NaturalNumber midRaised = new NaturalNumber2(mid);
                midRaised.power(r); // mid^R

                int check = midRaised.compareTo(n); // Variable that will compare and check mid^R to n
                if (check > 0) {
                    high.copyFrom(mid); //Updates High to narrow the search Range down
                } else if (check < 0) {
                    low.copyFrom(mid);// copies low from mid, makes sure binary search keeps going
                    low.increment(); // Makes sure low moves past mid if mid^r is less than n
                } else {
                    // If mid^r is exactly equal to n the root has successfully been found using binary search and interval halving.

                    n.copyFrom(mid);
                    return;
                }
            }

            // Set n to the lower bound if we haven't found an exact match
            n.copyFrom(low);
            n.decrement(); //The decrement here makes sure if low were to be incremented past it it would update n to account for this
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}