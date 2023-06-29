package ast;

import java.util.List;

public class HeaderDefinition extends Definition {
    
    private Type type;
    private String identifier;
    private Definition parameter;
    private List<Definition> parameters;

    public HeaderDefinition(Type type, String identifier, Definition parameter, List<Definition> parameters) {
        this.type = type;
        this.identifier = identifier;
        this.parameter = parameter;
        this.parameters = parameters;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifer(String identifer) {
        this.identifier = identifer;
    }

    public Definition getParameter() {
        return parameter;
    }

    public void setParameter(Definition parameter) {
        this.parameter = parameter;
    }

    public List<Definition> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<Definition> parameters) {
        this.parameters = parameters;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
