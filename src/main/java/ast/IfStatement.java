package ast;

import java.util.List;

public class IfStatement extends Statement {

    private List<Condition> conditions;
    private Statement statement;

    public IfStatement(List<Condition> conditions, Statement statement) {
        this.conditions = conditions;
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public List<Condition> getCondition() {
        return conditions;
    }

    public void setCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    } 
}