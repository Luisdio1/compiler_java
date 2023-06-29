package ast;

public class EmptyStatement extends Statement {

    public EmptyStatement() {
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
