package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionDeclaration extends Definition {
    
    private Type type;
    private String identifier;
    private List<Definition> parameterList;
    private List<Definition> definitionList;
    
    public FunctionDeclaration(Type type, String identifier, List<Definition> parameterList, List<Definition> definitionList) {
        this.identifier = identifier;
        this.parameterList = parameterList;
        this.type = type;
        this.definitionList = definitionList;
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

    public void setIdentifier(String identifer) {
        this.identifier = identifer;
    }
    
    public List<Definition> getParameterList() {
        List<Definition> reversedParameters = new ArrayList<>(parameterList);
        Collections.reverse(reversedParameters);
        return reversedParameters;
    }

    public void setParameterList(List<Definition> parameterList) {
        this.parameterList = parameterList;
    }

    public List<Definition> getDefinitionList() {
        List<Definition> reversedDefinitions = new ArrayList<>(definitionList);
        Collections.reverse(reversedDefinitions);
        return reversedDefinitions;
    }

    public void setDefinitionList(List<Definition> definitionList) {
        this.definitionList = definitionList;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
