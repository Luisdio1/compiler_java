package ast;

import java.util.List;

public class Program extends ASTNode {

	private Definition fd;

	public Program() {
	}

	public Program(Definition fd) {
		this.fd = fd;
	}

	public Definition getFd() {
		return fd;
	}

	public void setFd(Definition fd) {
		this.fd = fd;
	}

	@Override
	public void accept(ASTVisitor visitor) throws ASTVisitorException {
		visitor.visit(this);
	} 
}
