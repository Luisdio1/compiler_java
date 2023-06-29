package ast;

import java.util.List;

public class FunctionParameterDefinition extends Definition {
    
    private Type type;
    private List<String> identifier;
    private boolean ref;
    
    public FunctionParameterDefinition(Type type, List<String> identifier, boolean ref) {
        this.type = type;
        this.identifier = identifier;
        this.ref = ref;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifer) {
        this.identifier = identifer;
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
