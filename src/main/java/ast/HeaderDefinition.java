package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.Type;

public class HeaderDefinition extends Definition {
    
    private Type type;
    private String identifier;
    private List<Definition> parameters;

    public HeaderDefinition(Type type, String identifier, List<Definition> parameters) {
        this.type = type;
        this.identifier = identifier;
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

    public List<Definition> getParameters() {
        List<Definition> reversedParameters = new ArrayList<>(parameters);
        Collections.reverse(reversedParameters);
        return reversedParameters;
    }
    
    public void setParameters(List<Definition> parameters) {
        this.parameters = parameters;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
