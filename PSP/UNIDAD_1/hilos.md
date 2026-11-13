

 * Todos los hilos, siempre que acaban de hacer algo, que se salgan y que luego hagan otra cosa para que entren otros hilos, que espere, 
 * Los hilos demonio estan al margen de su proceso
 * si es un hilo, syncronized, y si son mas, entonces mejor utilizar semáforos
 

## explicacion semaforo

**Clase de cola:**
```
package mispaquetes;

import java.util.concurrent.Semaphore;

public class Cola {
	Semaphore miSemaforo;

	public Cola(Semaphore miSemaforo) {
		super();
		this.miSemaforo = miSemaforo;
	}
	
	public void pasar(String nombre) {				
		try {
			System.out.println("ESPERANDO semaforo hilo:"+nombre);
			this.miSemaforo.acquire();
			System.out.println("PASANDO semaforo hilo:"+nombre);
			System.out.println("Paso 1");
			Thread.sleep(1000);
			System.out.println("Paso 2");
			Thread.sleep(1000);
			System.out.println("Paso 3");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SOLTANDO semaforo hilo:"+nombre);
		this.miSemaforo.release();
		
	}

}

```




**Hilos**

```
package mispaquetes;

import java.util.concurrent.Semaphore;

public class Hilo extends Thread {

	Cola  miCola;
	
	public Hilo(Cola  miCola) {
		super();
		this.miCola = miCola;
	}
	
	public void run() {
		miCola.pasar(this.getName());
	}
	

	

}
```

**Principal**

```
package mispaquetes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Principal {

	public static void main (String args[]) {
		Semaphore miSemaforo = new Semaphore(2);
		Cola micola = new Cola(miSemaforo);
		
		ExecutorService miExecutor = Executors.newFixedThreadPool(10);
		
		for(int i=0; i<10; i++ ) {			
			miExecutor.submit(new Hilo(micola));			
		}
		
		miExecutor.shutdown();
	}
}
```
