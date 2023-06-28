package ast;

public class Program extends ASTNode {

	private Object fd;

	public Program() {
	}

	public Program(Object fd) {
		this.fd = fd;
	}

	public Object getFd() {
		return fd;
	}

	@Override
	public void accept(ASTVisitor visitor) throws ASTVisitorException {
		visitor.visit(this);
	} 
}
