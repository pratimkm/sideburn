package org.pratim.sideburn;

import org.junit.Test;

public class MMBVsH2Benchmark  {

//	@Benchmark
	@Test
	public void timeH2Operation()  {
		try {
			int reps = 50;
			H2Query h2q = new H2Query();
			for (int i = 0; i < reps; i++) {
				h2q.runH2Query();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
////	@Benchmark
////	@Test
//	public void timeMMBOperation() {
//		try {
//			int reps = 50;
//			QueryMaker qm = new QueryMaker();
//			
//			for (int i = 0; i < reps; i++) {
//				qm.lambdaQueryMaker();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void timeInMemOperation() {
		try {
			int reps = 50;
			InMemoryQueryMaker qm = new InMemoryQueryMaker();
			
			for (int i = 0; i < reps; i++) {
				qm.lambdaQueryMaker();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
