package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IfElseStatement extends Statement {

    private List<Condition> conditions;
    private Statement statement1;
    private Statement statement2;

    public IfElseStatement(List<Condition> conditions, Statement statement1, Statement statement2) {
        this.conditions = conditions;
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    public List<Condition> getCondition() {
        List<Condition> reversedConditions = new ArrayList<>(conditions);
        Collections.reverse(reversedConditions);
        return reversedConditions;
    }

    public void setCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Statement getStatement1() {
        return statement1;
    }

    public void setStatement1(Statement statement1) {
        this.statement1 = statement1;
    }

    public Statement getStatement2() {
        return statement2;
    }

    public void setStatement2(Statement statement2) {
        this.statement2 = statement2;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
