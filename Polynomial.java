import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Polynomial {
    double[] coeffs_;
    int[] exps_;

    public Polynomial() {
        coeffs_ = new double[]{};
        exps_ = new int[]{};
    }

    public Polynomial(double[] coeffs, int[] exps) {
        coeffs_ = coeffs;
        exps_ = exps;
    }

    public Polynomial(File file) throws Exception {
        this();
        try {
            Scanner reader = new Scanner(file);
            String[] tokens = reader.nextLine().replaceAll("-", "+-")
                .split("\\+");
            reader.close();

            for (String token : tokens) {
                String term[] = token.split("x");
                double coeff;
                int exp;
                if (Pattern.matches("x.*", token))
                    coeff = 1.;
                else if (Pattern.matches("^-x.*", token))
                    coeff = -1.;
                else
                    coeff = Double.parseDouble(term[0]);
                if (Pattern.matches("[^x]+", token))
                    exp = 0;
                else if (Pattern.matches(".*x", token))
                    exp = 1;
                else
                    exp = Integer.parseInt(term[1]);
                addTerm(coeff, exp);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void addTerm(double coeff, int exp) {
        int numCoeffs = coeffs_.length;

        // Check if exponent exists already
        for (int i = 0; i < numCoeffs; i++) {
            if (exps_[i] == exp) {
                coeffs_[i] += coeff;
                return;
            }
        }

        double[] newCoeffs = new double[numCoeffs + 1];
        int[] newExps = new int[numCoeffs + 1];

        for (int i = 0; i < numCoeffs; i++) {
            newCoeffs[i] = coeffs_[i];
            newExps[i] = exps_[i];
        }
        newCoeffs[numCoeffs] = coeff;
        newExps[numCoeffs] = exp;
        coeffs_ = newCoeffs;
        exps_ = newExps;
    }

    public Polynomial add(Polynomial addend) {
        int numCoeffs = coeffs_.length;
        Polynomial res = new Polynomial(new double[numCoeffs],
            new int[numCoeffs]);
        for (int i = 0; i < numCoeffs; i++) {
            res.coeffs_[i] = coeffs_[i];
            res.exps_[i] = exps_[i];
        }
        for (int i = 0; i < addend.coeffs_.length; i++)
            res.addTerm(addend.coeffs_[i], addend.exps_[i]);

        return res;
    }

    public Polynomial multiply(Polynomial mult) {
        Polynomial res = new Polynomial();
        for (int i = 0; i < coeffs_.length; i++) {
            for (int j = 0; j < mult.coeffs_.length; j++) {
                res.addTerm(coeffs_[i] * mult.coeffs_[j],
                    exps_[i] + mult.exps_[j]);
            }
        }
        return res;
    }

    public double evaluate(double x) {
        double res = 0.;
        for (int i = 0; i < coeffs_.length; i++)
            res += coeffs_[i] * Math.pow(x, exps_[i]);
        return res;
    }

    public void saveToFile(String name) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(name);
        for (int i = 0; i < coeffs_.length; i++) {
            if (i > 0 && coeffs_[i] >= 0.)
                writer.print('+');
            if (coeffs_[i] == -1.)
                writer.print('-');
            if (coeffs_[i] != 1. && coeffs_[i] != -1.)
                writer.print(coeffs_[i]);
            if (exps_[i] > 0)
                writer.print('x');
            if (exps_[i] > 1)
                writer.print(exps_[i]);
        }
        writer.print('\n');
        writer.close();
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.;
    }
}
