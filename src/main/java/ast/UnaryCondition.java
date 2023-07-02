package ast;

public class UnaryCondition extends Condition {

    private Operator operator;
    private Expression expression;
    private Condition condition;

    public UnaryCondition(Operator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public UnaryCondition(Operator operator, Condition condition) {
        this.operator = operator;
        this.condition = condition;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        if (expression != null) {
            this.expression = expression;
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
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
