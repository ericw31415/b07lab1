public class Polynomial {
    double[] coeffs_;

    public Polynomial() {
        coeffs_ = new double[]{0};
    }

    public Polynomial(double[] coeffs) {
        coeffs_ = coeffs;
    }

    public Polynomial add(Polynomial addend) {
        int maxLen = Math.max(coeffs_.length, addend.coeffs_.length);
        double[] coeffs = new double[maxLen];
        for (int i = 0; i < coeffs_.length; i++)
            coeffs[i] += coeffs_[i];
        for (int i = 0; i < addend.coeffs_.length; i++)
            coeffs[i] += addend.coeffs_[i];

        return new Polynomial(coeffs);
    }

    public double evaluate(double x) {
        double res = 0.;
        for (int i = coeffs_.length - 1; i >= 0; i--)
            res = res * x + coeffs_[i];
        return res;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.;
    }
}
