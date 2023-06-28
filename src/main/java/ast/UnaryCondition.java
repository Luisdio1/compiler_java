package ast;

public class UnaryCondition extends Condition {

    private Condition condition;
    private Operator operator;

    public UnaryCondition(Operator operator, Condition condition) {
        this.operator = operator;
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        if (condition != null) {
            this.condition = condition;
        } else {
            throw new IllegalArgumentException("Invalid condition");
        }
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        if (operator.equals("not")) {
            this.operator = operator;
        } else {
            throw new IllegalArgumentException("Invalid operator");
        }
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
