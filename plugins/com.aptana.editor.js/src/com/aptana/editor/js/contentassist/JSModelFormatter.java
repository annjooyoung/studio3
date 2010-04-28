package com.aptana.editor.js.contentassist;

import com.aptana.editor.js.model.FunctionElement;
import com.aptana.editor.js.model.ParameterElement;
import com.aptana.editor.js.model.PropertyElement;
import com.aptana.editor.js.model.ReturnTypeElement;
import com.aptana.util.StringUtil;

public class JSModelFormatter
{
	private JSModelFormatter()
	{
	}
	
	/**
	 * formatProperty
	 * 
	 * @param property
	 * @return
	 */
	public static String getDescription(PropertyElement property)
	{
		if (property instanceof FunctionElement)
		{
			return getDescription((FunctionElement) property);
		}
		
		StringBuffer buffer = new StringBuffer();
		
		// title
		buffer.append("<b>").append(property.getName()).append("</b>");
		
		// type
		addReturnTypes(buffer, property.getTypes(), "undefined");
		
		// description
		buffer.append("<br><br>");
		buffer.append(property.getDescription());
		
		return buffer.toString();
	}
	
	/**
	 * formatFunction
	 * 
	 * @param function
	 * @return
	 */
	public static String getDescription(FunctionElement function)
	{
		StringBuffer buffer = new StringBuffer();
		
		// title
		buffer.append("<b>").append(function.getName()).append("</b>").append("(");
		
		// append parameters
		boolean first = true;
		
		for (ParameterElement parameter : function.getParameters())
		{
			if (first == false)
			{
				buffer.append(", ");
			}
			else
			{
				first = false;
			}
			
			String usage = parameter.getUsage();
			boolean isOptional = ("zero-or-more".equals(usage) || "optional".equals(usage));
			boolean isRepeating = ("zero-or-more".equals(usage) || "one-or-more".equals(usage));
			
			if (isOptional)
			{
				buffer.append("[");
			}
			
			buffer.append("<b>").append(parameter.getName()).append("</b>");
			
			if (isRepeating)
			{
				buffer.append("+");
			}
			
			buffer.append(" : ").append(StringUtil.join("|", parameter.getTypes()));
			
			if (isOptional)
			{
				buffer.append("]");
			}
		}
		
		buffer.append(")");
		
		// return type
		addReturnTypes(buffer, function.getReturnTypes(), "void");
		
		// description
		buffer.append("<br><br>");
		buffer.append(function.getDescription());
		
		return buffer.toString();
	}

	private static void addReturnTypes(StringBuffer buffer, ReturnTypeElement[] returnTypes, String defaultType)
	{
		boolean first;
		
		if (returnTypes != null && returnTypes.length > 0)
		{
			buffer.append(" : ");
			
			first = true;
			
			for (ReturnTypeElement returnType : returnTypes)
			{
				if (first == false)
				{
					buffer.append("|");
				}
				else
				{
					first = false;
				}
				
				buffer.append(returnType.getType());
			}
		}
		else
		{
			buffer.append(" : ").append(defaultType);
		}
	}
	
	/**
	 * getName
	 * 
	 * @param property
	 * @return
	 */
	public static String getName(PropertyElement property)
	{
		if (property instanceof FunctionElement)
		{
			return getName((FunctionElement) property);
		}
		else
		{
			return property.getName();
		}
	}
	
	/**
	 * getName
	 * 
	 * @param function
	 * @return
	 */
	public static String getName(FunctionElement function)
	{
		return function.getName() + "()";
	}
}
