import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MainTest {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("请输入即将生成的题目数量：");
		int problems_num = scan.nextInt();
		System.out.print("请输入题目中数值（自然数、真分数和真分数分母）的范围：");
		int max_num = scan.nextInt();
		System.out.print("是否立即作答（y/n）：\n");
		String is_doNow = scan.next();

		LinkedHashMap<Integer, String> rightAnswerMap = new LinkedHashMap<Integer, String>();
		LinkedHashMap<Integer, String> exerciseMap = new LinkedHashMap<Integer, String>();
		ArrayList<Integer> rightRecord = new ArrayList<Integer>();
		ArrayList<Integer> wrongRecord = new ArrayList<Integer>();
		BuildExercise ce = new BuildExercise();
		ce.setMax_num(max_num);
		for (int i = 1; i <= problems_num; i++) {
			String problem = ce.create();
			exerciseMap.put(i, problem);
			// int i=1;String problem="(2/5-9)/10÷8";
			String ns = problem;// 必要时，修改随机生成的题目的符号方便计算;problem是原始随机生的题目，这是要显示的
			int rightbrackets;// 题目中右括号位置
			int leftbrackets;// 题目中左括号位置
			if (problem.contains(")")) {
				// 因为题目把a/b当成一个数会压入栈,这两个if语句解决随机生成的题目出现(a+b)/c，
				// 会先计算a+b=v,然后把v压入栈，再继续执行程序的时候会把/c当成一个数压入栈，再往后计算会出问题
				rightbrackets = problem.indexOf(")");
				leftbrackets = problem.indexOf("(");
				if (rightbrackets != problem.length() - 1 && problem.charAt(rightbrackets + 1) == '/') {
					StringBuilder sb = new StringBuilder(problem);
					if (leftbrackets - 1 > 0 && problem.charAt(leftbrackets - 1) == '÷')// 这个解决括号前面是÷号要变号
						sb.replace(rightbrackets + 1, rightbrackets + 2, "*");
					else
						sb.replace(rightbrackets + 1, rightbrackets + 2, "÷");
					ns = sb.toString();
					// System.out.println("problem="+problem);
					// System.out.println("ns="+ns);
				}
			}

			Calculator cal = new Calculator();
			String result = cal.calculate(ns);
			if (result != null) {
				result = cal.getFinalResult(result);
				System.out.println("T" + i + ":  " + problem + "=" + result);
				rightAnswerMap.put(i, result);
			} else {
				i--;
			}
			// ce.belongFraction("1+3/5");
			// ce.belongFraction("1-3/4");
			// ce.belongFraction("3/4*3/5");
			// ce.belongFraction("3/4÷3/5");

		} // end for

		if (is_doNow.equals("y")) {
			System.out.println("请按题号输入答案：");
			for (int i = 1; i <= problems_num; i++) {
				System.out.print("T" + i + ":  " + exerciseMap.get(i) + "=");
				String input = scan.next();
				if (rightAnswerMap.get(i).equals(input))
					rightRecord.add(i);
				else
					wrongRecord.add(i);
			}
			System.out.println("结果统计：");

			if (rightRecord.size() != 0) {
				System.out.print("Correct:" + rightRecord.size() + " (");
				for (int i = 0; i < rightRecord.size() - 1; i++)
					System.out.print(rightRecord.get(i) + ",");
				System.out.println(rightRecord.get(rightRecord.size() - 1) + ")");
			} else
				System.out.println("Correct:" + rightRecord.size());

			if (wrongRecord.size() != 0) {
				System.out.print("Wrong:" + wrongRecord.size() + " (");
				for (int i = 0; i < wrongRecord.size() - 1; i++)
					System.out.print(wrongRecord.get(i) + ",");
				System.out.println(wrongRecord.get(wrongRecord.size() - 1) + ")");
			} else
				System.out.println("Wrong:" + wrongRecord.size());
		}

	}
}
