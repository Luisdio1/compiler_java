package ast;

import java.util.ArrayList;
import java.util.List;

public class StatementGroup extends Statement {

    private List<Statement> statements;

    public StatementGroup() {
        this.statements = new ArrayList<Statement>();
    }

    public StatementGroup(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
