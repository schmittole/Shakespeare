package shakespeare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;


/**
 * 
 * @author oleschmitt
 *
 *	class MyReader to analyze and create new Sonnet
 */
public class MyReaderWriter {
	
	
	//the original shakespeare text
	String content;
	
	//the original shakespeare text as array
	String[] a;
	
	//the conditioned shakespeare text
	String[] aFinal;

	
	Sonnet s1;
	
	Sonnet s2;
	
	ArrayList<Sonnet> sonnets = new ArrayList<Sonnet>();
	
	ArrayList<Word> words = new ArrayList<Word>();
	
	ArrayList<String> wordsAsString = new ArrayList<String>();
	
	PriorityQueue<Word> wordsInOrder = new PriorityQueue<Word>();
	
	Sonnet mySonnet;
	
	Random rnd = new Random();
	
	int sonnetCounter = 1;
	
	ArrayList<Word> wordPool = new ArrayList<Word>();
	
	int arrayLength = 112;
	String[] aux = new String[arrayLength];
	
	int lineLength = 8;
	
	String firstWord="i";
	
	int probabilityFirst = 60;
	int probabilitySecond = 20;
	
	boolean showChoice = false;
	
	int wordsConsidered = 5;
	
	ArrayList<String> surveyArray = new ArrayList<String>();
	
	double noiseFactor = 1;
	
	int edgeWordAmountFactor = 100;

	private Scanner s;
	
	private int totalAmount = 0;
	
	double totalProbabilities = 0;
	
	
	public MyReaderWriter() throws IOException {
		content = readFile("Sonnets.rtf");
		a = content.split(" ");
		aFinal = new String[a.length];
		makeEndings();
		makeSonnets();
		addWords();
		setTotalAmount();
		initWordsAsStringList();
		initWordRelations();
		//printWords();
	}
	
	private void printWords() {
		double sum = 0;
		for(Word w : words) {
				sum += w.getProbability();
			w.print();
		}
		System.out.println("SUM "+sum);
	}
	
	public void incSonnetCounter() {
		sonnetCounter++;
	}
	
	public int getSonnetCounter() {
		return sonnetCounter;
	}
	
	public void setFirstWord(String w) {
		firstWord = w;
	}
	
	public void run() {
		
		
		//naive
		/**
		makeNewSonnetWithWordPoolAndPutRhymeWordsToEnd();
		mySonnet.printLyrical();
		*/
		
		s = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("[always confirm with enter!]");
		System.out.println();
		System.out.println();
		while(true) {
			System.out.println("PLEASE SET FIRST WORD OF SONNET!");
			String w = s.next();
			while(!wordsAsString.contains(w.toLowerCase())) {
				System.out.println("SHAKESPEARE NEVER USED SUCH A BORING WORD. TRY ANOTHER ONE!");
				w = s.next();
			}
			setFirstWord(w.toLowerCase());
			
			System.out.println("OKAY. GOOD CHOICE!");
			System.out.println("NOW, PLEASE SET A NOISE FACTOR. IT HAS TO BE BETWEEN 0 AND 100!");
			int i = 0;
			try {
			i = s.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("THAT WAS NOT A NUMBER! ANYWAY. NOISE FACTOR HAS BEEN SET TO 30.");
				i = 30;
			}
			while(i < 0 || i > 100) {
				System.out.println("NO, NO, NO. IT HAS TO BE BETWEEN 0 AND 100. TRY ONE MORE TIME!");
				try {
				i = s.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("THAT WAS NOT A NUMBER! ANYWAY. NOISE FACTOR HAS BEEN SET TO 30.");
					i = 30;
					break;
				}
			}
			setNoiseFactor(i);
			
			System.out.println("OKAY. GOOD CHOICE!");

			System.out.println("HERE IT COMES:");
			System.out.println();
			System.out.println();
			//second approach
			makeSonnetOneForwardThreeBackwardTwoForwardFourBackward();
			mySonnet.printLyrical();
			System.out.println("DO YOU WANT TO MAKE ANOTHER SONNET? (y/n)");
			w = s.next();
			while(!w.equals("y") && !w.equals("n")) {
				System.out.println("SORRY?! I DON'T UNDERSTAND!");
				w = s.next();
			}
			if(w.equals("n")) {
				System.out.println("ALRIGHT. ENOUGH SHAKESPEARE FOR TODAY. BYE!");
				System.out.println();
				System.out.println();
				break;
			}	
		}
		
		/**
		//third approach
		selectRhymeWordsThenWriteEachLineBackwards();
		mySonnet.printLyrical();
		*/

	}
	
	private void setProbabilitiesInRelToTotal() {
		setTotalProbabilities();
		for(Word w : words) {
			w.setProbability(w.getProbability()/totalProbabilities > 0 ? totalProbabilities : 1);
		}
	}
	
	private void setTotalProbabilities() {
		for(Word w : words) {
			totalProbabilities += w.getProbability();
		}
	}
	
	private double getTotalProbabilities() {
		return totalProbabilities;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	private void setTotalAmount() {
		int total = 0;
		for(Word w : words) {
			total += w.getAmount();
		}
		totalAmount = total;
	}
	
	private void setEdgeWordAmountFactor(int j) {
		edgeWordAmountFactor = j;
	}

	public void setNoiseFactor(int i) {
		//noiseFactor = (float)i/100;
		noiseFactor = (double)i;
	}
	
	public void putNoiseOnProbabilities() {
		for(Word w : words) {
			//w.noiseProbability(rnd.nextInt(2) == 0 ? (noiseFactor) : (noiseFactor));
			w.noiseProbability(rnd.nextInt(2) < 1 ? (getRandomNoiseInRange()) : (-getRandomNoiseInRange()));
		}
	}
	
	private double getRandomNoiseInRange() {
		double noise = noiseFactor > 0 ? (double)rnd.nextInt((int)noiseFactor) : 0;
		return noise/100;
	}
	
	private void initWordsAsStringList() {
		for(Word w : words) {
			wordsAsString.add(w.getWord());
		}
	}

	private void makeSonnetOneForwardThreeBackwardTwoForwardFourBackward() {
		aux[0] = firstWord;
		//first quartett
		//first line
		makeLineForward(1,8);
		//third line
		makeLineBackward(16,24);
		//second line
		makeLineForward(8,16);
		//fourth line
		makeLineBackward(24,32);
		
		//second quartett (same as first quartett)
		makeLineForward(32,40);
		makeLineBackward(48,56);
		makeLineForward(40,48);
		makeLineBackward(56,64);
		
		//second quartett (same as first quartett)
		makeLineForward(64,72);
		makeLineBackward(80,88);
		makeLineForward(72,80);
		makeLineBackward(88,96);
		
		//duett
		makeLineForward(96,104);
		makeLineBackward(104,112);
		
		//make sonnet with final array (aux)
		mySonnet = new Sonnet(aux, this, Integer.MAX_VALUE);
	}

	private void makeLineForward(int left, int right) {
		for(int i = left; i < right; i++) {
			aux[i] = getNextRightWord(i);
		}
	}
	
	private void makeLineBackward(int left, int right) {
		for(int i = right-2; i >=left; i--) {
			aux[i] = getNextLeftWord(i);
		}
	}
	
	private String getNextLeftWord(int pos) {
		//set probability from direct right neighbour 
		setProbabilitiesFromFirstWordsBack(getWordByString(aux[pos+1]));
		//set probability from second right neighbour
		if(wordsConsidered > 1 && pos % lineLength <= 5) {
			setProbabilitiesFromSecondWordsBack(getWordByString(aux[pos+2]));	
		}	
		//set probability from third right neighbour
		if(wordsConsidered > 2 && pos % lineLength <= 4 ) {
			setProbabilitiesFromThirdWordsBack(getWordByString(aux[pos+3]));	
		}
		//set probability from fourth right neighbour
		if(wordsConsidered > 3 && pos % lineLength <= 3 ) {
			setProbabilitiesFromFourthWordsBack(getWordByString(aux[pos+4]));	
		}
		//set probability from fifth right neighbour
		if(wordsConsidered > 4 && pos % lineLength <= 2 ) {
			setProbabilitiesFromFifthWordsBack(getWordByString(aux[pos+5]));	
		}
		
		setProbabilitiesInRelToTotal();
		putNoiseOnProbabilities();
		Word out = getWordWithHighestPriorityLessThanParam(Integer.MAX_VALUE);
		String finalOut = out.getWord();

		//not allowed: same word in direct sequence -> choose a random word
		while(finalOut.equals(aux[pos+1])) {
			finalOut = words.get(rnd.nextInt(words.size())).getWord();
		}
		
		resetAllWordProbabilities();
		return finalOut;
	}
	
	private void setProbabilitiesFromFifthWordsBack(Word w) {
		if(w != null) {
			for(FifthBackwardEdge f : w.getFifthBackwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromFourthWordsBack(Word w) {
		if(w != null) {
			for(FourthBackwardEdge f : w.getFourthBackwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromThirdWordsBack(Word w) {
		if(w != null) {
			for(ThirdBackwardEdge f : w.getThirdBackwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}

	private void setProbabilitiesFromSecondWordsBack(Word w) {
		if(w != null) {
			for(SecondBackwardEdge f : w.getSecondBackwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}

	private void setProbabilitiesFromFirstWordsBack(Word w) {
		if(w != null) {
			for(FirstBackwardEdge f : w.getFirstBackwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}

	private String getNextRightWord(int pos) {
		//set probability from direct left neighbour 
		setProbabilitiesFromFirstWords(getWordByString(aux[pos-1]));
		//set probability from second left neighbour
		if(wordsConsidered > 1 && pos > 1) {
			setProbabilitiesFromSecondWords(getWordByString(aux[pos-2]));	
		}	
		//set probability from third left neighbour
		if(wordsConsidered > 2 && pos > 2) {
			setProbabilitiesFromThirdWords(getWordByString(aux[pos-3]));	
		}
		//set probability from fourth left neighbour
		if(wordsConsidered > 3 && pos > 3) {
			setProbabilitiesFromFourthWords(getWordByString(aux[pos-4]));	
		}
		//set probability from fifthleft neighbour
		if(wordsConsidered > 4 && pos > 4) {
			setProbabilitiesFromFifthWords(getWordByString(aux[pos-5]));	
		}
		
		setProbabilitiesInRelToTotal();
		putNoiseOnProbabilities();
		//printWords();
		
		Word out = getWordWithHighestPriorityLessThanParam(Integer.MAX_VALUE);
		String finalOut = out.getWord();
		
		//not allowed: same word in direct sequence -> choose a random word
		while(finalOut.equals(aux[pos-1])) {
			finalOut = words.get(rnd.nextInt(words.size())).getWord();
		}
		
		//word == end of line ?
		if(pos%lineLength == 7) {
			//fix if word has no rhyme word
			while(out.getRhymeEdges().size() < 1) {
				out = words.get(rnd.nextInt(words.size()-1));
			}
			//add rhyme word to next next or next line
			int nextLineScalar = 2; // -> next next line
			if(pos == 13*lineLength-1) { //this is the 13th line -> rhyme word to 14th line
				nextLineScalar = 1; // -> next line
			} 
			//put rhyme word to next or next next line
			aux[pos+lineLength*nextLineScalar] = out.getBestRhymeWord().getWord()+"\\";
			//add line break
			finalOut = out.getWord() + "\\";
		}
		resetAllWordProbabilities();
		return finalOut;
	}

	/**
	 * 
	 * @param max. highest priority has to be under max
	 * @return the word with highest priority and under param
	 */
	private Word getWordWithHighestPriorityLessThanParam(double max) {
		Word best = words.get(0);
		for(Word w : words) {
			if(w.getProbability() < max && w.getProbability() > best.getProbability()) {
				best = w;
			}
		}
		return best;
	}
	
	private void setProbabilitiesFromFirstWords(Word w) {
		if(w != null) {
			for(FirstForwardEdge f : w.getFirstForwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromSecondWords(Word w) {
		if(w != null) {
			for(SecondForwardEdge f : w.getSecondForwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromThirdWords(Word w) {
		if(w != null) {
			for(ThirdForwardEdge f : w.getThirdForwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromFourthWords(Word w) {
		if(w != null) {
			for(FourthForwardEdge f : w.getFourthForwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void setProbabilitiesFromFifthWords(Word w) {
		if(w != null) {
			for(FifthForwardEdge f : w.getFifthForwardEdges()) {
				f.getTarget().incProbability(f.getWeight());
			}
		}
	}
	
	private void resetAllWordProbabilities() {
		for(Word w : words) {
			w.resetProbability();
		}
	}
	
	public void initWordRelations() {
		for(Sonnet s : sonnets) {
			s.initSonnetWords();
		}
	}
	
	public void printNewSonnet() {
		mySonnet.printLyrical();
	}
	
	/**
	 * make sonnet with random words (naive approach)
	 */
	public void makeNewSonnet() {
		int arrayLength = 140;
		String[] aux = new String[arrayLength];
		for(int i = 1; i <= arrayLength; i++) {
			aux[i-1] = words.get(rnd.nextInt(words.size()-1)).getWord();
			if(i%10==0) {
				aux[i-1] += "\\";
			}
		}
		mySonnet = new Sonnet(aux, this, Integer.MAX_VALUE);
	}

	/**
	 * this method creates the original shakespeare sonnets from the aFinal string array
	 * 
	 */
	private void makeSonnets() {
		int sonnetCounter = 0;
		int curWord=0;
		int lineCounter = 0;
		//System.out.println(aFinal.length);
		for(int i=0;i<aFinal.length;i++) {
			if(aFinal[i].contains("\\") || aFinal[i].contains("}")) {
				//there are sonnets (3) in the original with more or less than 14 lines ! (=in this implementation these ones are not considered)
				int linesToBeThere = sonnets.size() == 98 ? 15 : sonnets.size() == 125 ? 12 : 14;
				lineCounter++;
				//end of sonnet ?
				if(lineCounter%linesToBeThere==0) {
					lineCounter = 0;
					sonnetCounter++;
					//make a new sonnet
					sonnets.add(new Sonnet(Arrays.copyOfRange(aFinal, curWord, i+1), this, sonnetCounter));
					curWord = i;
				}
			}
		}
	}

	
	/**
	 * this method fixes the endings of strings in the a array if necessary and puts it to the aFinal
	 */
	private void makeEndings() {
		for(int i=0;i<a.length;i++) {
			if(a[i].contains("}")) {
				a[i].replace('}', '\\');
			}
			if(a[i].contains("uc0")) {
				//System.out.println("HERE");
				a[i] = a[i].substring(0, a[i].length() - (a[i].length()-a[i].indexOf("uc0")));
				a[i] += "_____________END_OF_LINE";
			}
			if(a[i].contains("u82")) {
				//System.out.println("HERE");
				a[i] = a[i].substring(0, a[i].length() - (a[i].length()-a[i].indexOf("u82")));
				a[i] += "_____________END_OF_LINE";
			}	
			aFinal[i] = new String(a[i]);
		}
	}
	
	/**
	 * this method reads the text file with the original shakespeare sonnets
	 * @param file, file to read from
	 * @return the file as a string
	 * @throws IOException
	 */
	private String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	        	char[] a = line.toCharArray();
	        	//System.out.println(a.length);
	        	if(a.length > 0 && a.toString().contains("\\")) {
	        		
	        	}
	        	if(a.length > 0 && a[0] != '{' && a[0] != '\\') { 
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        	}
	        	
	        }
	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	/**
	 * checks if string is in words
	 * 
	 * @param s to check
	 * @return matching word | null
	 */
	private Word inWords(String s) {
		for(Word w : words) {
			if(w.getWord().equals(s)) {
				return w;
			}
		}
		return null;
	}
	
	/**
	 * puts all words from all sonnets to the words list (inc amount of word if already in list)
	 */
	private void addWords() {
		for(Sonnet so : sonnets) {
			ArrayList<ArrayList<String>> curSonnet = so.getSonnet();
			for(ArrayList<String> curLine : curSonnet) {
				for(String s : curLine) {
					String aux;
					//delete interpunction if necessary
					if(s.contains(",") || s.contains("!") || s.contains(".") || s.contains("?") || s.contains(";") || s.contains(":")) {
						aux = new String(s.substring(0, s.length()-1).toLowerCase());
					} else {
						aux = new String(s).toLowerCase();
					}
					if(aux != null && aux.isEmpty()) {
						continue;
					}
					//try to find word in words
					Word look = inWords(aux);
					//is alreay in words -> inc amount
					if(look != null) {
						look.incAmount();
						//add current sonnet number to sonnet numbers of words (word appears in sonnet XYZ)
						look.updateInSonnet(so.getNumber());
					} else {
						//add new word to word list
						words.add(new Word(aux, this, so.getNumber(), edgeWordAmountFactor));
					}		
				}
			}
		}
	}
	
	/**
	 * get a word by its string
	 * @param s, string value to find in words
	 * @return the word with matching string value | null
	 */
	public Word getWordByString(String s) {
		if(s == null || s.isEmpty() || (s.length() == 1 && s.equals(" "))) {
			return null;
		}
		String aux = new String(s);
		aux = aux.toLowerCase();
		//delete spaces if necessary
		if(aux.charAt(aux.length()-1) == ' ') {
			aux = aux.substring(0, aux.length()-1).toLowerCase();
		}
		//delete punctuation if necessary
		if(s.contains(",") || s.contains("!") || s.contains(".") || s.contains("?") || s.contains(";") || s.contains(":") || (s.length() > 0 && s.charAt(s.length()-1) == ' ')) {
			aux = new String(s.substring(0, s.length()-1).toLowerCase());
		}
		//look in word list
		for(Word w : words) {
			if(w.getWord().equals(aux)) {
				return w;
			}
		}
		return null;
	}
}
