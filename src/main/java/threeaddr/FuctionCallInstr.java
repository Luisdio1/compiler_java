package threeaddr;

import java.util.ArrayList;
import java.util.List;

public class FuctionCallInstr implements Instruction{
	
	private List<String> params;
	private String name;
	private String result;
	
	public FuctionCallInstr(ArrayList<String> params, String name, String result) {
		this.params = params;
		this.name = name;
		this.result = result;
	}
	
	public FuctionCallInstr(String name, String result) {
		params = new ArrayList<String>();
		this.name = name;
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String emit() {
		if (this.params.isEmpty()){
			return result + " = call " + this.name + " , 0"; 
		} else {
			StringBuilder sb = new StringBuilder();
			for (String param : params) {
				sb.append("param " + param);
				sb.append("\n");
			}
			sb.append(result + " = call " + this.name + " , " + params.size());
			return sb.toString();
		}
	}

}
