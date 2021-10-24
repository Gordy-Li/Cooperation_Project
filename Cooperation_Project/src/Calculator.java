import java.util.Stack;


/**
 * ������
 */
public class Calculator {

    /**
     * ����ջ�����ڴ洢�����������
     */
    private Stack<Character> symbolStack = null;

    /**
     * ��������������������ʽ(������)�����ؼ�����
     *
     * @param numStr �������ʽ(������)
     */
    public String calculate(String numStr) {
        numStr = removeStrSpace(numStr); // ȥ���ո�
        // ����������ʽβ��û�С�=���ţ�����β����ӡ�=������ʾ������
        if (numStr.length() > 1 && !"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
            numStr += "=";
        }
        // �����ʽ�Ƿ�Ϸ�
        if (!isStandard(numStr)) {
            System.err.println("�����������ʽ����");
            return "0";
        }
        // ��ʼ��ջ
        /*
          ����ջ�����ڴ洢���ʽ�еĸ�������
         */
        Stack<String> numberStack = new Stack<>();
        symbolStack = new Stack<>();
        // ���ڻ������֣���Ϊ���ֿ����Ƕ�λ��
        StringBuilder temp = new StringBuilder();
        // �ӱ��ʽ�ĵ�һ���ַ���ʼ����
        for (int i = 0; i < numStr.length(); i++) {
            char ch = numStr.charAt(i); // ��ȡһ���ַ�
            if (isNumber(ch) || ch == '/') { // ����ǰ�ַ�������
                temp.append(ch); // ���뵽���ֻ�����
            } else { // �����ֵ����
                String tempStr = temp.toString(); // �����ֻ���תΪ�ַ���
                if (!tempStr.isEmpty()) {
                    //long num = Long.parseLong(tempStr); // �������ַ���תΪ��������
                    numberStack.push(tempStr); // ������ѹջ
                    temp = new StringBuilder(); // �������ֻ���
                }
                // �ж�����������ȼ�������ǰ���ȼ�����ջ�������ȼ������ȰѼ���ǰ��������
                //comparePri(ch)�Ƚ����ȼ��������ǰ�������ջ��Ԫ����������ȼ����򷵻�true�����򷵻�false
                while (!comparePri(ch) && !symbolStack.empty()) {
                    String a = numberStack.pop(); // ��ջ��ȡ�����֣�����ȳ�
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
                    // ȡ�������������Ӧ���㣬���ѽ��ѹջ������һ������
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
                        case '��':
                            if (f1.fenzi==0){
                                System.err.println("������Ŀ���ܳ��ֳ���Ϊ0�������");
                                return null;
                            }
                            numberStack.push(f2.divide(f1).print());
                            break;
                        default:
                            break;
                    }
                } // whileѭ������
                if (ch != '=') {
                    symbolStack.push(ch); // ������ջ
                    if (ch == ')') { // ȥ����
                        symbolStack.pop();
                        symbolStack.pop();
                    }
                }
            }//else����
        } // forѭ������

        return numberStack.pop(); // ���ؼ�����
    }

    /**
     * ȥ���ַ����е����пո�
     */
    private String removeStrSpace(String str) {
        return str != null ? str.replaceAll(" ", "") : "";
    }

    /**
     * ����������ʽ�Ļ����Ϸ��ԣ����Ϸ���true������false
     */
    private boolean isStandard(String numStr) {
        if (numStr == null || numStr.isEmpty()) // ���ʽ����Ϊ��
            return false;
        Stack<Character> stack = new Stack<>(); // �����������ţ�������������Ƿ�ƥ��
        boolean b = false; // �������'='�����Ƿ���ڶ��
        for (int i = 0; i < numStr.length(); i++) {
            char n = numStr.charAt(i);
            // �ж��ַ��Ƿ�Ϸ�
            if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                    || "+".equals(n + "") || "-".equals(n + "")
                    || "*".equals(n + "") || "��".equals(n + "") || "/".equals(n + "")
                    || "=".equals(n + ""))) {
                return false;
            }
            // ��������ѹջ������������������Ž���ƥ��
            if ("(".equals(n + "")) {
                stack.push(n);
            }
            if (")".equals(n + "")) { // ƥ������
                if (stack.isEmpty() || !"(".equals((char) stack.pop() + "")) // �����Ƿ�ƥ��
                    return false;
            }
            // ����Ƿ��ж��'='��
            if ("=".equals(n + "")) {
                if (b)
                    return false;
                b = true;
            }
        }
        // ���ܻ���ȱ�������ŵ����
        if (!stack.isEmpty())
            return false;
        // ���'='���Ƿ���ĩβ
        return "=".equals(numStr.charAt(numStr.length() - 1) + "");
    }

    /**
     * �ж��ַ��Ƿ���0-9������
     */
    private boolean isNumber(char num) {
        return num >= '0' && num <= '9';
    }

    /**
     * �Ƚ����ȼ��������ǰ�������ջ��Ԫ����������ȼ����򷵻�true�����򷵻�false
     */
    private boolean comparePri(char symbol) {
        if (symbolStack.empty()) { // ��ջ����ture
            return true;
        }

        // �������ȼ�˵�����Ӹߵ��ͣ�:
        // ��1��: (
        // ��2��: * ��
        // ��3��: + -
        // ��4��: )

        char top = symbolStack.peek(); // �鿴��ջ�����Ķ���ע�ⲻ�ǳ�ջ
        if (top == '(') {
            return true;
        }
        // �Ƚ����ȼ�
        switch (symbol) {
            case '(': // ���ȼ����
                return true;
            case '*':
            case '��': {
                // ���ȼ���+��-��
                return top == '+' || top == '-';
            }
            case '+':
            case '-':
                return false;
            case ')': // ���ȼ����
                return false;
            case '=': // ������
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
            return a / b + "��" + a % b + "/" + b;
        } else if (a < b && -a > b && (-a) % b != 0) {
            return "-" + (-a) / b + "��" + (-a) % b + "/" + b;
        } else if (b == 1)
            return a + "";
        else
            return a + "/" + b;
    }


}