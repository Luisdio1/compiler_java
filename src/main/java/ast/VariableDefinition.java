package ast;

import java.util.List;

public class VariableDefinition extends Definition {

    private String identifier;
    private List<String> identifiers;
    private Type type;
    private List<Integer> dimensions;

    public VariableDefinition(String identifier, List<String> identifiers, Type type, List<Integer> dimensions) {
        this.identifier = identifier;
        this.identifiers = identifiers;
        this.type = type;
        this.dimensions = dimensions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifer) {
    	this.identifier = identifer;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifier(List<String> identifiers) {
    	this.identifiers = identifiers;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
    	this.type = type;
    }

    public List<Integer> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Integer> dimensions) {
    	this.dimensions = dimensions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
