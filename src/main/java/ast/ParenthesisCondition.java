package ast;

public class ParenthesisCondition extends Condition {

    private Condition condition;

    public ParenthesisCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setExpression(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
