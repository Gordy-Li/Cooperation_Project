import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MainTest {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("�����뼴�����ɵ���Ŀ������");
		int problems_num = scan.nextInt();
		System.out.print("��������Ŀ����ֵ����Ȼ������������������ĸ���ķ�Χ��");
		int max_num = scan.nextInt();
		System.out.print("�Ƿ���������y/n����\n");
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
			// int i=1;String problem="(2/5-9)/10��8";
			String ns = problem;// ��Ҫʱ���޸�������ɵ���Ŀ�ķ��ŷ������;problem��ԭʼ���������Ŀ������Ҫ��ʾ��
			int rightbrackets;// ��Ŀ��������λ��
			int leftbrackets;// ��Ŀ��������λ��
			if (problem.contains(")")) {
				// ��Ϊ��Ŀ��a/b����һ������ѹ��ջ,������if�����������ɵ���Ŀ����(a+b)/c��
				// ���ȼ���a+b=v,Ȼ���vѹ��ջ���ټ���ִ�г����ʱ����/c����һ����ѹ��ջ�����������������
				rightbrackets = problem.indexOf(")");
				leftbrackets = problem.indexOf("(");
				if (rightbrackets != problem.length() - 1 && problem.charAt(rightbrackets + 1) == '/') {
					StringBuilder sb = new StringBuilder(problem);
					if (leftbrackets - 1 > 0 && problem.charAt(leftbrackets - 1) == '��')// ����������ǰ���ǡº�Ҫ���
						sb.replace(rightbrackets + 1, rightbrackets + 2, "*");
					else
						sb.replace(rightbrackets + 1, rightbrackets + 2, "��");
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
			// ce.belongFraction("3/4��3/5");

		} // end for

		if (is_doNow.equals("y")) {
			System.out.println("�밴�������𰸣�");
			for (int i = 1; i <= problems_num; i++) {
				System.out.print("T" + i + ":  " + exerciseMap.get(i) + "=");
				String input = scan.next();
				if (rightAnswerMap.get(i).equals(input))
					rightRecord.add(i);
				else
					wrongRecord.add(i);
			}
			System.out.println("���ͳ�ƣ�");

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
