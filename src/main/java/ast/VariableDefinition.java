package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.Type;

public class VariableDefinition extends Definition {

    private List<String> identifiers;
    private Type type;

    public VariableDefinition(List<String> identifiers, Type type) {
        this.identifiers = identifiers;
        this.type = type;
    }

    public List<String> getIdentifiers() {
        List<String> reversedIdentifiers = new ArrayList<>(identifiers);
        Collections.reverse(reversedIdentifiers);
        return reversedIdentifiers;
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

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
