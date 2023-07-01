package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhileStatement extends Statement {

    private List<Condition> conditions;
    private Statement statement;

    public WhileStatement(List<Condition> conditions, Statement statement) {
        this.conditions = conditions;
        this.statement = statement;
    }

    public List<Condition> getCondition() {
        List<Condition> reversedConditions = new ArrayList<>(conditions);
        Collections.reverse(reversedConditions);
        return reversedConditions;
    }

    public void setCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
