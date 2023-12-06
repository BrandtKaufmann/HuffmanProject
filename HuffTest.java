import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
class HuffTest{
	//TRUE/FALSE method that will check if a char's int value is within
	//the accepted range, or is the end of file char
	static Boolean checkchar(int checkthis){
		if (checkthis >= 7 && checkthis <= 254 || checkthis == 4 || checkthis == -1){
			return true;
		}else{
			return false;
		}
	}

	public static void main(String[] args) {
		int f1Char;
		int f2Char;
		int f1Index = 0;
		int f2Index = 0;
		if (args.length==2){
			try{
				FileReader file1 = new FileReader(args[0]);
				FileReader file2 = new FileReader(args[1]);
				//every char read must be indexed to provide helpful and accurate FAIL print statement
				f1Char = file1.read();
				f1Index++;
				f2Char = file2.read();
				f2Index++;
				/* While at least one file is not at its end,(read() returns -1 for EOF char)
				read a single char, if it's in accepted values, read from other file and compare
				if not, read until accepted value found from file1
				Repeat process for file2, and compare values
				*/ 
				while (f1Char != -1 || f2Char != -1){
					while (!checkchar(f1Char)){
						f1Char = file1.read();
						f1Index++;
					}
					while (!checkchar(f2Char)){
						f2Char = file2.read();
						f2Index++;
					}
					if (f1Char==f2Char){
						f1Char = file1.read();
						f1Index++;
						f2Char = file2.read();
						f2Index++;
					}else{
						System.out.println("FAIL input " + (char)f1Char + " @ " + f1Index + " output " + (char)f2Char + " @ " + f2Index);
						System.exit(0);
					}
				} 
				System.out.println("PASS");
			}catch(FileNotFoundException fnfe){
				System.out.println("error");
			}catch(IOException owie){
				System.out.println("error");
			}
		}else{
			System.err.println("ERROR: incorrect number of files");
		}
	}
}