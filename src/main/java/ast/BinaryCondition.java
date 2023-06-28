package ast;

public class BinaryCondition extends Condition {

    private Condition condition1;
    private Condition condition2;
    private Operator operator;

    public BinaryCondition(Operator operator, Condition condition1, Condition condition2) {
        this.operator = operator;
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    public Condition getCondition1() {
        return condition1;
    }

    public void setCondition1(Condition condition1) {
        this.condition1 = condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public void setCondition2(Condition condition2) {
        this.condition2 = condition2;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        if (operator.equals("and") || operator.equals("or")) {
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
