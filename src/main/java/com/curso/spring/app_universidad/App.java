package com.curso.spring.app_universidad;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.curso.spring.app_universidad.entidades.Universidad;

public class App 
{
    public static void main( String[] args )
    {
    
	
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    	Universidad universidad = context.getBean("universidad", Universidad.class);
    	universidad.setContext(context);
    	
    	universidad.start();
    	
    	context.close();
      
    }
}
