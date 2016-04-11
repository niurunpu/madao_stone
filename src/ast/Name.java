package ast;
import stone.Token;
import stone.StoneException;

public class Name extends ASTLeaf
{
    public Name(Token t) { super(t); }
    public String name() { return token().getText(); }
    public Object eval(Environment env) 
    {
    	Object value = env.get(name());
    	if (null == value)
    		throw new StoneException("undefined name: " + name(), this);
    	return value;
    }
}

