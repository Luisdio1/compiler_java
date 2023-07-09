package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionCallExpression extends Expression {

    private String identifier;
    private List<Expression> expressions;

    public FunctionCallExpression(String identifier, List<Expression> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifer) {
        this.identifier = identifer;
    }

    public List<Expression> getExpressions() {
        List<Expression> reversedExpressions = new ArrayList<>(expressions);
        Collections.reverse(reversedExpressions);
        return reversedExpressions;
    }

    public void setExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
