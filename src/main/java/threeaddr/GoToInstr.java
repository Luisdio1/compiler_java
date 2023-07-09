package threeaddr;

public class GoToInstr implements Instruction {
	
	public LabelInstr target;
	
	public GoToInstr() {
		this.target = null;
	}
	
	public GoToInstr(LabelInstr target) {
		this.target = target;
	}
	
	public LabelInstr getTarget() {
		return target;
	}
	
	public void setTarget(LabelInstr target) {
		this.target = target;
	}

	@Override
	public String emit() {
		if (target == null) {
			return "goto _";
		} else {
			return "goto " + target.getName();
		}
	}
}