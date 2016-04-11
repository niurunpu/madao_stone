package ast;
import java.util.List;

public class WhileStmnt extends ASTList
{
	private static final int FALSE = 0 ;
    public WhileStmnt(List<ASTree> c) { super(c); }
    public ASTree condition() { return child(0); }
    public ASTree body() { return child(1); }
    public String toString() { return "(while " + condition() + " " + body() + ")"; }
    public Object eval(Environment env)
    {
    	Object result = 0;
    	for(;;)
    	{
    		Object c = condition().eval(env);
    		if (c instanceof Integer && ((Integer)c).intValue() == FALSE)
    			return result;
    		else 
    			result = body().eval(env);   				
    	}
    }
}
