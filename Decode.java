import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;

class Tree{
   /* creation of member class TreeNode, this follows the specs of TestTree.java, but nodes 
    * carry string values instead of ints.
	* It's likely better to use ints, but using strings made it easier for me to think about.
	* each node has left and right variables and their value. 
	* Value method exists so we can access the value in other methods within class Tree
	* 
	*/
	private class TreeNode {
		String value;
		TreeNode left;
		TreeNode right;

		TreeNode(String v) {
			value = v;
			left = null;
			right = null;
		}
		String value() { return value; }
	}
	
	// Format method returns a single char as a string when given its int value as a string
	// if ints were used in the tree and hashmap this would be unneccessary
	String format(String num){
		int ourint = Integer.valueOf(num);
		String output = "";
		return (output + (char) ourint);
	}

	TreeNode root;
	public void Tree() { 
		root = null; 
	}
	// Overloaded add method, if there are no nodes yet, we cannot pass a TreeNode object, but this will create
	// root and then call the beefy add method starting from root.
	public void add(String code, String input) {
			if (root == null) {
			root = new TreeNode("Root");
		}
		add(root, input, code, 0);
	}

	private void add(TreeNode top, String binary, String code, int index) {
	   /* We index through the binary, going left for 0 and right for 1
		* we lug around the character value for the binary, and an index variable to know which char we're looking at,
		* we index through our string, first checking to make sure we're not out of range. If we have a 0, we go left, 
		* if 1, we go right, creating new nodes intialized to 0, wherever we go. When we reach end of the binary string,
		* we check 1 or 0, then make a new node to the right or left, accordingly.
		*/

		if (binary.length()-1 == index){ //base case, end of recursion
			if(binary.charAt(index) == '1'){
				top.right = new TreeNode(code);
			}else if (binary.charAt(index) == '0'){
				top.left = new TreeNode(code);
			}
		}
		
		else if (binary.charAt(index) == '1') { //right
			if (top.right == null) {
				top.right = new TreeNode("0");
			} 
			add(top.right, binary, code, ++index);

		} else if (binary.charAt(index) == '0') { //left
			if (top.left == null) {
				top.left = new TreeNode("0");
			} 
			add(top.left, binary, code, ++index);
		}
	}//close add

	/*
	* Decode method: takes in the binary string we're looking to decode, and the location for the output.
	* For each char, move through the tree left or right for 0 or 1, respectively until a leaf node is reached.
	* the leaf node's value will be added to the growing StringBuilder that is the output, the current will
	* be reset to the root, and we move through the tree again starting from the next char. 
	* This while loop will continue till end of file, printing every char except the end of transmission char.
	*/
	void decode(String Filename, String outputFileName){
		try{
        	StringBuilder output = new StringBuilder();
        	TreeNode current = root;
        	int binaryVal = 0;
        	FileReader input = new FileReader(Filename);
        	Boolean notEndOfTransmission = true;
        	while ((binaryVal = input.read()) != -1 && notEndOfTransmission) {
                if (binaryVal == '0') {
                    current = current.left;
                } else {
                    current = current.right;
                }
                if (current.left == null && current.right == null) {
                    if (!current.value.equals("4")){
	                    output.append(format(current.value));
	                    current = root;
					}else {
						notEndOfTransmission = false;
					}
                }
            }
        File outputFile = new File(outputFileName);
        FileWriter write = new FileWriter(outputFile);
        write.write(output.toString());
        write.close();
        }catch(FileNotFoundException foo){
        	System.err.println("FileNotFoundException");
        }catch(IOException IOE){
        	System.err.println("IOException");
        }
	}
}

class Decode{
	
   /* Codebook method from Encode.java, takes in the file name of the codebook file, 
 	* reads through and creates a HashMap with each line of the codebook,
    * returns the HashMap.
	*/
	static HashMap<String, String> codeBook(String Filename){
		try{
			File Fi = new File(Filename);
			Scanner scan = new Scanner(Fi);
			HashMap<String, String> codebook = new HashMap<String, String>();
			String[] vals;
			String line;
			while (scan.hasNextLine()){
				line = scan.nextLine();
				vals = line.split(":");
				codebook.put(vals[0], vals[1]);
			}
			scan.close();
			return codebook;
		}catch(FileNotFoundException f){
			System.err.println("FileNotFoundException");
		}
		return null;
	}

	public static void main(String[] args) {
		if (args.length >1){
			HashMap<String, String> code = codeBook("codebook");
			Tree myTree = new Tree();
			for (Map.Entry<String, String> entry : code.entrySet()) {
	            String key = entry.getKey();
	            String value = entry.getValue();
	            myTree.add(key, value);
	        }
			myTree.decode(args[0], args[1]);
		}else{
			System.err.println("incorrect number of arguments");
		}
	}
}