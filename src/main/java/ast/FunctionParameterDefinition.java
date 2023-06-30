package ast;

import java.util.List;

public class FunctionParameterDefinition extends Definition {
    
    private String type;
    private List<String> identifiers;
    private boolean ref;
    
    public FunctionParameterDefinition(String type, List<String> identifiers, boolean ref) {
        this.type = type;
        this.identifiers = identifiers;
        this.ref = ref;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
