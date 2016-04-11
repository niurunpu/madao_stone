package ast;
import java.util.List;

public class IfStmnt extends ASTList
{
	private static final int FALSE = 0;
    public IfStmnt(List<ASTree> c) { super(c); }
    public ASTree condition() { return child(0); }
    public ASTree thenBlock() { return child(1); }
    public ASTree elseBlock() { return numChildren() > 2 ? child(2) : null; }
    
    public String toString() 
    {
    	return "(if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")";
    }
    public Object eval(Environment env)
    {
    	Object cond = condition().eval(env);
    	if (cond instanceof Integer && ((Integer)cond).intValue() != FALSE)
    	    return thenBlock().eval(env);
    	else 
    	{
    		ASTree b = elseBlock();
    		if (null == b)
    			return 0;
    		else 
    			return b.eval(env);
    	}
    }
    
}
