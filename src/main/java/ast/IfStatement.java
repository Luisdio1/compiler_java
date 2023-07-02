package ast;

public class IfStatement extends Statement {

    private Condition condition;
    private Statement statement;

    public IfStatement(Condition condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    } 
}