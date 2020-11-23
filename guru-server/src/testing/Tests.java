package testing;

public class Tests {
	public static void main(String[] args) {
		
		//SOC
		try {
			SOC_Test soc = new SOC_Test();
		} catch (Exception e) {
			System.out.println("Failed SOC_Test");
			e.printStackTrace();
		}
		
		//Schedule
		try {
			Schedule_Test sch = new Schedule_Test();
		} catch (Exception e) {
			System.out.println("Failed Schedule_Test");
			e.printStackTrace();
		}
		
		//Schedule
		try {
			Connection_Test ct = new Connection_Test();
		} catch (Exception e) {
			System.out.println("Failed Connection_Test");
			e.printStackTrace();
		}
		
	}
}
