import java.io.File;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = {-9, -2};
        int[] e2 = {4, 1};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial prod = p1.multiply(p2);
        System.out.println("prod(0.2) = " + prod.evaluate(0.2));
        try {
            Polynomial f = new Polynomial(new File("input.txt"));
            System.out.println("f(1.9) = " + f.evaluate(1.9));
            f.saveToFile("output.txt");
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
