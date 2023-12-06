import java.util.HashMap;
import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter; 


class Encode{
  /* codeBook method: this method takes in a file name, and will read through each line
	* with a Scanner, splitting lines on colon character, and then will pass the first and second values
	* in the HashMap, first one as an Integer and second one as a String, as these will sometimes
	* be too long for Integers. once we've put all the lines' values into the HashMap, return the HashMap.
	*/
	static HashMap<Integer, String> codeBook(String Filename){
		try{
			File Fi = new File(Filename);
			Scanner scan = new Scanner(Fi);
			HashMap<Integer, String> codebook = new HashMap<Integer, String>();
			String[] vals;
			String line;
			while (scan.hasNextLine()){
				line = scan.nextLine();
				vals = line.split(":");
				codebook.put(Integer.valueOf(vals[0]), vals[1]);
			}
			scan.close();
			return codebook;
		}catch(FileNotFoundException f){
			System.err.println("FileNotFoundException");
		}
		return null;
	}

	public static void main(String[] args) {
		if (args.length>1){
			String input = args[0];
			String outF = args[1];
			HashMap<Integer, String> code = codeBook("codebook");
			try{
				StringBuilder output = new StringBuilder();
				FileReader file = new FileReader(input);
				int fchar = 0;
				while(fchar != -1){//-1 is the endOfFile value returned by FileReader
					fchar = file.read();
					// we want to ignore chars that are out of range.
					// chars that are out of the range of our codebook 
					// will return null when get is called, so out output String will be unchanged. 
					if (code.get(fchar) != null){
						output.append(code.get(fchar));
					}
				}
				// Once the EOF value is passed, we add an EOT char, 
				// make our fileWriter, and pass out output string
				// to the designated file,  
				output.append(code.get(4));
				File outputFile = new File(outF);
				FileWriter write = new FileWriter(outputFile);
				write.write(output.toString());
				write.close();
			}catch(FileNotFoundException fnfe){
				System.out.println("error");
			}catch(IOException owie){
				System.out.println("error");
			}
		}else{
			System.err.println("incorrect number of arguments");
		}
	}
}