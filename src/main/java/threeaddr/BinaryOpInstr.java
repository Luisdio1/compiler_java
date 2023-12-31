package threeaddr;

import ast.Operator;

public class BinaryOpInstr implements Instruction{
	
	private Operator op;
	private String arg1;
	private String arg2;
	private String result;

	public BinaryOpInstr(Operator op, String arg1, String arg2, String result) {
		super();
		this.op = op;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.result = result;
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String emit() {
		return result + " = " + arg1 + " " + op + " " + arg2;
	}
}