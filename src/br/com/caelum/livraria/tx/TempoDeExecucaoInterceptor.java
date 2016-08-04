package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@SuppressWarnings("serial")
@Log
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable{

	@AroundInvoke
	public Object imprimeLog(InvocationContext context) throws Exception{
		
		long antes = System.currentTimeMillis();

		Object proceder = context.proceed();

		long depois = System.currentTimeMillis();
		
		long resultado = depois - antes;

		System.out.println("Método executado: " + context.getMethod().getName() + " Tempo execução: " + resultado);
		
		return proceder;
	}
}
