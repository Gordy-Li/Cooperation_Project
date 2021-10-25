import java.util.Objects;
import java.util.Random;


public class BuildExercise {
    String[] sign = {"+", "-", "*", "÷", "/"};
    //String filename = "";
    int max_num;
    Random random = new Random();
    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    public String create() {
        /*
          随机生成四则运算题目
          @Param []
         * @return java.lang.String
         */
        StringBuilder str = new StringBuilder();
        int local = random.nextInt(3);
        for (int j = 0; j < 3; j++) {
            //System.out.println(local);
            if (local == 0 && j == 0) {
                str.append("(");
            } else if (local == 2 && j == 1) {
                str.append("(");
            }
            str.append(random.nextInt(max_num) % max_num + 1);//产生指定范围随机数
            if (local == 0 && j == 1) {
                str.append(")");
            }
            if (local == 2 && j == 2) {
                str.append(")");
            }
            String signElement = sign[random.nextInt(5)];//产生随机运算符号
            str.append(signElement);
            if (Objects.equals(signElement, "/")) {
                str.append(random.nextInt(max_num) % max_num + 1);
                signElement = sign[random.nextInt(5)];
                while (true) {
                    if (!Objects.equals(signElement, "/")) {
                        str.append(signElement);
                        break;
                    }
                    signElement = sign[random.nextInt(5)];
                }
            }

        }
        str = new StringBuilder(str.substring(0, str.length() - 1));
        //System.out.println(str);
        return str.toString();
    }

}
