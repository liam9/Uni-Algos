


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP 
{
   private static String pattern;
   private static int DFA[][];
   private static char[] position; //holds the row that each unique letter represents in the DFA
   private static int unique; //The amount of unique letters in pattern

   
   public KMP(String pattern)
   { 
		pattern = this.pattern;
   }
   
   public static int[][] createDFA(){
		position = new int[pattern.length];
		unique = 0;
		//Find the unique letters in the pattern
		for(int i = 0; i < pattern.length; i++){
			for(int j = 0; j < unique; j++){
				if(pattern.charAt(i) == position[j])
					break
			}
			position[unique] = pattern.charAt(i);
			unique++;
		}
		DFA = new int[unique][pattern.length];
		
	   for(int y = 0; y < pattern; y++){
		    for(int x = 0; x < pattern; x++){
				DFA[y][x] = 0;
	   }
	   
	   
	   //putting the linear odering of the pattern into the DFA
	   for(int i = 0; i < pattern.length-1; i++){
		   DFA[convertToNum(pattern.charAt(i))][i] = convertToNum(pattern.charAt(i+1));
	   }
	   
	   boolean match = true;
	   char[][] patternsWithinPattern = new char[pattern.length][pattern.length];
	   //checking for patterns within the pattern string
		for(int i = 2; i < pattern.length/2; i++){
			for(int j = i+1; j < pattern.length-i; j++){
				for(int k = 0; k < i; k++){
					if(!(pattern.charAt(k) == pattern.cahrAt(j+k))){
						match = false;
					}
				}
				
			}
			
		}
	   
	   
   }
   
   //converts to the letters numeric value
   public staitic int convertToNum(char letter){
	   for(int i = 0; i < unique; i++){
		   if(pattern.charAt(i) == letter){
			   return i;
		   }
		   return -1; //letter not found
		   
	   }
   }
   public static int search(String txt)
   {  
   	   return 0;
   }

        
  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}
			
			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}
			
			//System.out.println(text);
			
		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
		
		
	}
}




