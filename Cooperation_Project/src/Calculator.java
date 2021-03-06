import java.util.Stack;

public class Calculator {
    private Stack<Character> symbolStack = null;
    public String calculate(String numStr) {
        numStr = removeStrSpace(numStr); // 去除空格
        // 如果算术表达式尾部没有‘=’号，则在尾部添加‘=’，表示结束符
        if (numStr.length() > 1 && !"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
            numStr += "=";
        }
        // 检查表达式是否合法
        if (!isStandard(numStr)) {
            System.err.println("错误：算术表达式有误！");
            return "0";
        }
        // 初始化栈
        /*
          数字栈：用于存储表达式中的各个数字
         */
        Stack<String> numberStack = new Stack<>();
        symbolStack = new Stack<>();
        // 用于缓存数字，因为数字可能是多位的
        StringBuilder temp = new StringBuilder();
        // 从表达式的第一个字符开始处理
        for (int i = 0; i < numStr.length(); i++) {
            char ch = numStr.charAt(i); // 获取一个字符
            if (isNumber(ch) || ch == '/') { // 若当前字符是数字
                temp.append(ch); // 加入到数字缓存中
            } else { // 非数字的情况
                String tempStr = temp.toString(); // 将数字缓存转为字符串
                if (!tempStr.isEmpty()) {
                    //long num = Long.parseLong(tempStr); // 将数字字符串转为长整型数
                    numberStack.push(tempStr); // 将数字压栈
                    temp = new StringBuilder(); // 重置数字缓存
                }
                // 判断运算符的优先级，若当前优先级低于栈顶的优先级，则先把计算前面计算出来
                //comparePri(ch)比较优先级：如果当前运算符比栈顶元素运算符优先级高则返回true，否则返回false
                while (!comparePri(ch) && !symbolStack.empty()) {
                    String a = numberStack.pop(); // 出栈，取出数字，后进先出
                    String b = numberStack.pop();
                    Fraction f1;
                    Fraction f2;
                    if (a.contains("/")) {
                        String[] alist = a.split("/");
                        f1 = new Fraction(Integer.parseInt(alist[0]), Integer.parseInt(alist[1]));
                    } else {
                        f1 = new Fraction(Integer.parseInt(a), 1);
                    }
                    if (b.contains("/")) {
                        String[] blist = b.split("/");
                        f2 = new Fraction(Integer.parseInt(blist[0]), Integer.parseInt(blist[1]));
                    } else {
                        f2 = new Fraction(Integer.parseInt(b), 1);
                    }
                    // 取出运算符进行相应运算，并把结果压栈进行下一次运算
                    switch (symbolStack.pop()) {
                        case '+':
                            numberStack.push(f2.plus(f1).print());
                            break;
                        case '-':
                            numberStack.push(f2.minus(f1).print());
                            break;
                        case '*':
                            numberStack.push(f2.multiply(f1).print());
                            break;
                        case '÷':
                            if (f1.fenzi==0){
                                System.err.println("错误：题目不能出现除数为0的情况！");
                                return null;
                            }
                            numberStack.push(f2.divide(f1).print());
                            break;
                        default:
                            break;
                    }
                } // while循环结束
                if (ch != '=') {
                    symbolStack.push(ch); // 符号入栈
                    if (ch == ')') { // 去括号
                        symbolStack.pop();
                        symbolStack.pop();
                    }
                }
            }//else结束
        } // for循环结束

        return numberStack.pop(); // 返回计算结果
    }

    /**
     * 去除字符串中的所有空格
     */
    private String removeStrSpace(String str) {
        return str != null ? str.replaceAll(" ", "") : "";
    }

    /**
     * 检查算术表达式的基本合法性，符合返回true，否则false
     */
    private boolean isStandard(String numStr) {
        if (numStr == null || numStr.isEmpty()) // 表达式不能为空
            return false;
        Stack<Character> stack = new Stack<>(); // 用来保存括号，检查左右括号是否匹配
        boolean b = false; // 用来标记'='符号是否存在多个
        for (int i = 0; i < numStr.length(); i++) {
            char n = numStr.charAt(i);
            // 判断字符是否合法
            if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                    || "+".equals(n + "") || "-".equals(n + "")
                    || "*".equals(n + "") || "÷".equals(n + "") || "/".equals(n + "")
                    || "=".equals(n + ""))) {
                return false;
            }
            // 将左括号压栈，用来给后面的右括号进行匹配
            if ("(".equals(n + "")) {
                stack.push(n);
            }
            if (")".equals(n + "")) { // 匹配括号
                if (stack.isEmpty() || !"(".equals((char) stack.pop() + "")) // 括号是否匹配
                    return false;
            }
            // 检查是否有多个'='号
            if ("=".equals(n + "")) {
                if (b)
                    return false;
                b = true;
            }
        }
        // 可能会有缺少右括号的情况
        if (!stack.isEmpty())
            return false;
        // 检查'='号是否不在末尾
        return "=".equals(numStr.charAt(numStr.length() - 1) + "");
    }

    /**
     * 判断字符是否是0-9的数字
     */
    private boolean isNumber(char num) {
        return num >= '0' && num <= '9';
    }

    /**
     * 比较优先级：如果当前运算符比栈顶元素运算符优先级高则返回true，否则返回false
     */
    private boolean comparePri(char symbol) {
        if (symbolStack.empty()) { // 空栈返回ture
            return true;
        }

        // 符号优先级说明（从高到低）:
        // 第1级: (
        // 第2级: * ÷
        // 第3级: + -
        // 第4级: )

        char top = symbolStack.peek(); // 查看堆栈顶部的对象，注意不是出栈
        if (top == '(') {
            return true;
        }
        // 比较优先级
        switch (symbol) {
            case '(': // 优先级最高
                return true;
            case '*':
            case '÷': {
                // 优先级比+和-高
                return top == '+' || top == '-';
            }
            case '+':
            case '-':
                return false;
            case ')': // 优先级最低
                return false;
            case '=': // 结束符
                return false;
            default:
                break;
        }
        return true;
    }

    String getFinalResult(String str) {
        if (!str.contains("/"))
            return str;
        String[] part = str.split("/");
        int a = Integer.parseInt(part[0]);
        int b = Integer.parseInt(part[1]);
        if (a == b)
            return "1";
        else if (a > b && a % b != 0) {
            //System.out.println(a + "/" + b);
            return a / b + "’" + a % b + "/" + b;
        } else if (a < b && -a > b && (-a) % b != 0) {
            return "-" + (-a) / b + "’" + (-a) % b + "/" + b;
        } else if (b == 1)
            return a + "";
        else
            return a + "/" + b;
    }


}