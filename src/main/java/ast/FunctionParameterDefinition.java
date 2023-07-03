package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.Type;

public class FunctionParameterDefinition extends Definition {
    
    private Type type;
    private List<String> identifiers;
    private boolean ref;
    
    public FunctionParameterDefinition(Type type, List<String> identifiers, boolean ref) {
        this.type = type;
        this.identifiers = identifiers;
        this.ref = ref;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public List<String> getIdentifiers() {
        List<String> reversedIdentifiers = new ArrayList<>(identifiers);
        Collections.reverse(reversedIdentifiers);
        return reversedIdentifiers;
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
