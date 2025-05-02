package org.mrbag.LinkCompresor.Entity.StringKeyGenerator;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;

class TestHashInstrumental implements Callable<Map<String, Integer>>{

	final int a;
	
	final int count;
	
	public TestHashInstrumental(int a, int count) {
		this.a = a;
		this.count = count; 
	}
	

	@Override
	public Map<String, Integer> call() throws Exception {
		Map<String, Integer> result = new LinkedHashMap<>();
		Set<String> sets = new HashSet<>();
		for (int b = 1 ; b < 32; b++) {
			int col = 0;
			sets.clear();
			for (int i = 0 ; i < count; i++) {
				String st = UtilsConver.IntToString(XorShiftStringGenerator.hash(i,a, b));
				if(sets.contains(st)) {
					col ++;
//					System.out.println(st);
				}
				else 
					sets.add(st);
			}
			result.put(String.format("{%d, %d}", a, b), col);
		}
		return result;
	}
	
}

public class XorShiftStringGenerator implements IStringKeyGenerator {

	int position = 0;
	
	public XorShiftStringGenerator(int position) {
		this.position = position;
	}
	//XORShift32
	private int hash() {
		int buf = position;
		
		buf ^= buf << 31;
		buf ^= buf >> 15;
		buf ^= buf << -16;
		
		return buf;
	}
	
	
	static int hash(int p, int a, int b) {
		int buf = p;
		
		buf ^= buf << a;
		buf ^= buf >> b;
		buf ^= buf << b - a;
		
		return buf;
	}
	
	
	@Override
	public String next() {
		String s =UtilsConver.IntToString(hash());
		position++;
		return s; 
	}

	@Override
	public String get() {
		return UtilsConver.IntToString(hash());
	}


	@Override
	public boolean isValid() {
		return position+1 < 2000000;
	}
	
	public static void _main(String[] args)  {
		ExecutorService exe = Executors.newCachedThreadPool();
		
		Set<Future<Map<String, Integer>>> futs = new LinkedHashSet<>();
		
		Map<String, Integer> fin = new LinkedHashMap<String, Integer>();
		
		for (int i = 1; i < 32; i ++) {
			futs.add(exe.submit(new TestHashInstrumental(i, 2000000)));
		}
		for (Future<Map<String, Integer>> fres : futs) {
			Map<String, Integer> res;
			try {
				res = fres.get();
				
				Map.Entry<String, Integer> buf = res.entrySet().stream().min(Map.Entry.comparingByValue()).get();
				
				fin.put(buf.getKey(), buf.getValue());
				
				System.out.printf("\t[%s]: %d\n", buf.getKey(), buf.getValue());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Map.Entry<String, Integer> buf = fin.entrySet().stream().min(Map.Entry.comparingByValue()).get();
		System.out.printf("\n\n\tRESULT[%s]: %d\n", buf.getKey(), buf.getValue());
		exe.shutdown();
	}

}
