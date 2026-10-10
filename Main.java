import java.util.*;

class Parser {
	public static List<List<Integer>> parse(String program) {
		List<List<Integer>> repr = new LinkedList();

		int plength = program.length();
		int i = 0;

		while (i < plength) {
			// find next ";"
			int j = i;
			while (j<plength) {
				if (program.charAt(j) == ';') {
					break;
				}
				j++;
			}

			List<String> currentLine = new LinkedList();

			int c_index = i;
			String c_token = "";
			// ignore the semicolon
			while (c_index <j-1) {
				if (program.charAt(c_index) == ' ' && !c_token.equals("")) {
					currentLine.add(c_token.trim());
					c_token = "";
				} else {
					c_token = c_token + program.charAt(c_index);
				}
				c_index++;
				i++;
			}
			// add last character
			c_token = c_token + program.charAt(c_index);
			currentLine.add(c_token.trim());
			i++;


			int lineTokLength = currentLine.size();

			List<Integer> currentLineRepr = new LinkedList();
			for(int k=0; k<lineTokLength; ++k) {
				if (currentLine.get(k).equals("push")) {
					currentLineRepr.add(0);
					k++;
					currentLineRepr.add(Integer.parseInt(currentLine.get(k)));
				} else if (currentLine.get(k).equals("pop")) {
					currentLineRepr.add(1);
					k++;
				}
			}

			repr.add(currentLineRepr);

			i++;
		}

		return repr;
	}
}

class TinyStackMachine {
	List<Integer> stack = new LinkedList();
	List<List<Integer>> intermediate_repr = new LinkedList();

	void setProgram(String program) {
		intermediate_repr = Parser.parse(program);
	}

	void run() {
		for (int i = 0; i<intermediate_repr.size(); i++) {
			List<Integer> stmt = intermediate_repr.get(i);
			int op = stmt.get(0);

			if (op == 0) {
				int v = stmt.get(1);
				stack.add(v);
			} else if (op == 1) {
				if (stack.size() == 0) {
					System.out.println("underflow");
					continue;
				}
				int r = stack.remove(stack.size()-1);
				System.out.println(r);
			} else {
				System.out.println("invalid op: "+op);
			}
		}
	}
}







public class Main {
	public static void main(String[] args) {
		TinyStackMachine tsm = new TinyStackMachine();
		tsm.setProgram("push 5; push 8; push 10; pop; pop; pop; pop;");
		tsm.run();
	}
}
