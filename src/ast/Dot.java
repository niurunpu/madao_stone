package ast;
import java.util.List;
import stone.NestedEnv;
import stone.StoneException;
import ast.StoneObject.AccessException;

public class Dot extends Postfix
{
    public Dot(List<ASTree> c) { super(c); }
    public String name() { return ((ASTLeaf)child(0)).token().getText(); }
    public String toString() { return "." + name(); }
    public Object eval(Environment env, Object value)
    {
    	String member = name();
    	if (value instanceof ClassInfo)
    	{
    		if ("new".equals(member))
    		{
    			ClassInfo ci = (ClassInfo)value;
    			NestedEnv e = new NestedEnv(ci.environment());
    			StoneObject so = new StoneObject(e);
    			e.putNew("this", so);
    			initObject(ci, e);
    			return so;
    		}
    	}
    	else if (value instanceof StoneObject)
    	{
    		try
    		{
    			return ((StoneObject)value).read(member);
    		}
    		catch (AccessException e) { throw new StoneException("bad member access: " + member, this); } //!!!!!!??????
    	}
    	throw new StoneException("bad member access: " + member, this);
    }
    protected void initObject(ClassInfo ci, Environment env)
    {
    	if (ci.superClass() != null)
    		initObject(ci.superClass(), env);
    	ci.body().eval(env);
    } 
}
