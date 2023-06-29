package ast;

import java.util.List;

public class FunctionCallStatement extends Statement {

    private String identifier;
    private Expression expression;
    private List<Expression> expressions;

    public FunctionCallStatement(String identifier, Expression expression, List<Expression> expressions) {
        this.identifier = identifier;
        this.expression = expression;
        this.expressions = expressions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifer) {
        this.identifier = identifer;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
