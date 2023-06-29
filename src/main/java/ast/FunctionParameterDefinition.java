package ast;

import java.util.List;

public class FunctionParameterDefinition extends Definition {
    
    private Type type;
    private String identifier;
    private List<String> identifiers;
    private boolean ref;
    
    public FunctionParameterDefinition(Type type, String identifier, List<String> identifiers, boolean ref) {
        this.type = type;
        this.identifier = identifier;
        this.identifiers = identifiers;
        this.ref = ref;
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

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public boolean getRef() {
        return ref;
    }

    public void setRef(boolean ref) {
        this.ref = ref;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
