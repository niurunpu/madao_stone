package ast;
import java.util.List;

public class ClassBody extends ASTList
{
    public ClassBody(List<ASTree> c) { super(c); }
    public Object eval(Environment env)
    {
    	for (ASTree t : this)
    		t.eval(env);
    	return null;
    }
}
