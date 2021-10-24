
public class Fraction {
    int fenzi;
    int fenmu;
    private Fraction temp;

    public Fraction(int a, int b) {
        /*
          根据分子，分母构建分数
          @Param [a, b]
         * @return
         */
        fenzi = a;
        fenmu = b;
    }

    Fraction plus(Fraction r) {
        /*
          分数加法
          @Param [r]
         * @return Fraction
         */
        temp = new Fraction(0, 0);
        temp.fenzi = fenzi * r.fenmu + fenmu * r.fenzi;
        temp.fenmu = fenmu * r.fenmu;
        return temp;
    }

    Fraction minus(Fraction r) {
        /*
          分数减法
          @Param [r]
         * @return Fraction
         */
        temp = new Fraction(0, 0);
        temp.fenzi = fenzi * r.fenmu - fenmu * r.fenzi;
        temp.fenmu = fenmu * r.fenmu;
        return temp;
    }

    Fraction multiply(Fraction r) {
        /*
          分数乘法
          @Param [r]
         * @return Fraction
         */
        temp = new Fraction(0, 0);
        temp.fenzi = fenzi * r.fenzi;
        temp.fenmu = fenmu * r.fenmu;
        return temp;
    }

    Fraction divide(Fraction r) {
        /*
          分数除法
          @Param [r]
         * @return Fraction
         */
        temp = new Fraction(0, 0);
        temp.fenzi = fenzi * r.fenmu;
        temp.fenmu = fenmu * r.fenzi;
        return temp;
    }

    String print() {
        /*
          计算结果化简
          @Param []
         * @return void
         */
        if (fenzi == 0) {
            return "0";
        } else {
            int n = Math.max(fenzi, fenmu);
            int maxn = 0;
            for (int i = 1; i <= n; ++i) {//约分
                if (fenzi % i == 0 && fenmu % i == 0)
                    maxn = i;
            }
            int a = fenzi / maxn;
            int b = fenmu / maxn;
            if (a == b)
                return "1";
//            else if (a > b) {
//                //System.out.println(a + "/" + b);
//                return a / b + "’" + a % b + "/" + b;
//            }
            else if(b==1)
                return a+"";
            else
                return a + "/" + b;
        }
    }//print()结束
}