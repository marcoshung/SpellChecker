import java.io.*;
public class SpellChecker {
	public static void main(String[] args) throws IOException{
		RedBlackTree dictionary = new RedBlackTree();
		
		//reads the dictionary
		BufferedReader words = new BufferedReader(new FileReader("Dictionary.txt"));
		double startTime;
		double endTime;
		String word;
		startTime = System.currentTimeMillis();
		
		//inserts all words from the dictionary file into the dictionary RBT
		while((word = words.readLine()) != null) {
			dictionary.insert(word.trim());
		}
		
		System.out.println();
		String sin = (String) dictionary.root.key;
		endTime = System.currentTimeMillis();
		
		//prints compilation time
		System.out.println("Time to compile dictionary : " + (endTime - startTime));
		BufferedReader poem = new BufferedReader(new FileReader("poem.txt"));
		String line = "";
		String spellCheck = "";
		startTime = System.currentTimeMillis();
		
		//reads all words from the poem
		while((line = poem.readLine()) != null) {
			
			//splits lines into words and ignores punctuation
			String[] lineWords = line.toLowerCase().replaceAll("[^a-zA-Z ]","").split(" ");
			
			//checks every word from the array of lineWords and if it cannot find it in lookup then the word is misspelled
			for(String s : lineWords) {
				if(dictionary.lookup(s.trim()) == null && !s.isEmpty()) {
					spellCheck += s + " ";
				}
			}
		}
		endTime = System.currentTimeMillis();
		
		System.out.println("Misspelled Words : " + spellCheck);
		System.out.println("Time to spell check poem : " + (endTime - startTime));
	}
}
