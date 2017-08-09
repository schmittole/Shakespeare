package shakespeare;

import java.util.ArrayList;

/**
 * 
 * @author oleschmitt
 *
 *	class to represent a word with its properties
 */

public class Word implements Comparable<Word> {
	
	//the word as string
	private String word;
	
	// the amount of the word in the original shakespeare
	private int amount;
	
	//the edges to all my rhyme words
	ArrayList<RhymeEdge> rhymeWords;
	
	//the edges to my forward neighbours
	ArrayList<FirstForwardEdge> firstWords;
	ArrayList<SecondForwardEdge> secondWords;
	ArrayList<ThirdForwardEdge> thirdWords;
	ArrayList<FourthForwardEdge> fourthWords;
	ArrayList<FifthForwardEdge> fifthWords;
	
	//the edges to my backward neighbours
	ArrayList<FirstBackwardEdge> firstWordsBack;
	ArrayList<SecondBackwardEdge> secondWordsBack;
	ArrayList<ThirdBackwardEdge> thirdWordsBack;
	ArrayList<FourthBackwardEdge> fourthWordsBack;
	ArrayList<FifthBackwardEdge> fifthWordsBack;
	
	MyReaderWriter readerWriter;
	
	//numbers of sonnets which this word is part of
	ArrayList<Integer> inSonnet = new ArrayList<Integer>();
	
	//probability to get chosen as next word in the production of a new sonnet
	double probability = 0;

	double myNoise;
	
	double myOldProbability = 0;

	int wordAmountFactor;
	
	
	/**
	 * 
	 * @param w, word as string
	 * @param r, my reader
	 * @param n, first sonnet where the word has been found in 
	 * 
	 * 	constructor
	 */
	public Word(String w, MyReaderWriter r, int n, int wordAmountF) {
		wordAmountFactor = wordAmountF;
		inSonnet.add(n);
		readerWriter = r;
		word = w;
		amount = 1;
		rhymeWords = new ArrayList<RhymeEdge>();

		firstWords = new ArrayList<FirstForwardEdge>();
		secondWords = new ArrayList<SecondForwardEdge>();
		thirdWords = new ArrayList<ThirdForwardEdge>();
		fourthWords = new ArrayList<FourthForwardEdge>();
		fifthWords = new ArrayList<FifthForwardEdge>();
		
		
		firstWordsBack = new ArrayList<FirstBackwardEdge>();
		secondWordsBack = new ArrayList<SecondBackwardEdge>();
		thirdWordsBack = new ArrayList<ThirdBackwardEdge>();
		fourthWordsBack = new ArrayList<FourthBackwardEdge>();
		fifthWordsBack = new ArrayList<FifthBackwardEdge>();
		
	}
	
	public double getAmountInPerc() {
		int amountOfAllWords = readerWriter.getTotalAmount();
		return (double)amount/amountOfAllWords;
	}
	
	public void incProbability(double i) {
		probability += i;
	}
	
	public void resetProbability() {
		probability = 0;
	}
	
	public double getProbability() {
		return probability;
	}
	
	public void setProbability(double p) {
		probability = p;
	}
	
	public void noiseProbability(double i) {
		myOldProbability = probability;
		myNoise = i;
		probability = probability * (1+i);
	}
	
	public void updateInSonnet(int i) {
		if(!inSonnet.contains(i)) {
			inSonnet.add(i);
		}
	}
	
	public Word getMeIfInSameSonnet(ArrayList<Integer> s) {
		for(Integer i : inSonnet) {
			for(Integer j : s) {
				if(i == j) {
					return this; 
				}
			}
		}
		return null;
	}
	
	public ArrayList<Integer> getMySonnetNumbers() {
		return inSonnet;
	}
	
	public void printAllMySonnetNumbers() {
		System.out.println(word.toUpperCase());
		for(Integer i : inSonnet) {
			System.out.println(i);
		}
	}
	
	
	///*****************************************
	// add edges/words. if already exists, inc weight of edge to word
	///*****************************************
	
	
	/**
	 * 
	 * @param w, word which is a new rhyme word
	 * 
	 * 	add a new rhymeword. if already exists, inc weight of edge to rhyme word
	 */
	public void addRhymeWord(Word w) {
		for(RhymeEdge r : rhymeWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		rhymeWords.add(new RhymeEdge(this, w, wordAmountFactor));
	}
	
	public void addFirstWordBack(Word w) {
		for(FirstBackwardEdge r : firstWordsBack) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		firstWordsBack.add(new FirstBackwardEdge(this, w,wordAmountFactor));
	}
	
	public void addSecondWordBack(Word w) {
		for(SecondBackwardEdge r : secondWordsBack) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		secondWordsBack.add(new SecondBackwardEdge(this, w,wordAmountFactor));
	}
	
	public void addThirdWordBack(Word w) {
		for(ThirdBackwardEdge r : thirdWordsBack) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		thirdWordsBack.add(new ThirdBackwardEdge(this, w,wordAmountFactor));
	}
	
	public void addFourthWordBack(Word w) {
		for(FourthBackwardEdge r : fourthWordsBack) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		fourthWordsBack.add(new FourthBackwardEdge(this, w,wordAmountFactor));
	}
	
	public void addFifthWordBack(Word w) {
		for(FifthBackwardEdge r : fifthWordsBack) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		fifthWordsBack.add(new FifthBackwardEdge(this, w,wordAmountFactor));
	}
	
	public void addFirstWord(Word w) {
		for(FirstForwardEdge r : firstWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		firstWords.add(new FirstForwardEdge(this, w,wordAmountFactor));
	}
	
	public void addSecondWord(Word w) {
		for(SecondForwardEdge r : secondWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		secondWords.add(new SecondForwardEdge(this, w,wordAmountFactor));
	}
	
	public void addThirdWord(Word w) {
		for(ThirdForwardEdge r : thirdWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		thirdWords.add(new ThirdForwardEdge(this, w,wordAmountFactor));
	}
	
	public void addFourthWord(Word w) {
		for(FourthForwardEdge r : fourthWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		fourthWords.add(new FourthForwardEdge(this, w,wordAmountFactor));
	}
	
	public void addFifthWord(Word w) {
		for(FifthForwardEdge r : fifthWords) {
			if(r.getTarget().getWord().equals(w.getWord())) {
				r.incWeight();
				return;
			}
		}
		fifthWords.add(new FifthForwardEdge(this, w,wordAmountFactor));
	}

	/**
	 * 
	 * @param w
	 * @return if there is a rhyme connection to word w
	 */
	public boolean hasRhymeEdgeTo(Word w) {
		for(RhymeEdge e : rhymeWords) {
			if(e.getTarget().getWord().equals(w.getWord())) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<RhymeEdge> getRhymeEdges() {
		return rhymeWords;
	}
	
	public ArrayList<FirstForwardEdge> getFirstForwardEdges() {
		return firstWords;
	}
	
	public ArrayList<SecondForwardEdge> getSecondForwardEdges() {
		return secondWords;
	}
	
	public ArrayList<ThirdForwardEdge> getThirdForwardEdges() {
		return thirdWords;
	}
	
	public ArrayList<FourthForwardEdge> getFourthForwardEdges() {
		return fourthWords;
	}
	
	public ArrayList<FifthForwardEdge> getFifthForwardEdges() {
		return fifthWords;
	}
	
	public ArrayList<FirstBackwardEdge> getFirstBackwardEdges() {
		return firstWordsBack;
	}
	
	public ArrayList<SecondBackwardEdge> getSecondBackwardEdges() {
		return secondWordsBack;
	}
	
	public ArrayList<ThirdBackwardEdge> getThirdBackwardEdges() {
		return thirdWordsBack;
	}
	
	public ArrayList<FourthBackwardEdge> getFourthBackwardEdges() {
		return fourthWordsBack;
	}
	
	public ArrayList<FifthBackwardEdge> getFifthBackwardEdges() {
		return fifthWordsBack;
	}
	
	public String getWord() {
		return word;
	}
	
	public void incAmount() {
		amount++;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void print() {
		System.out.println(word+"\t ["+amount+"]");
		System.out.println("probability OLD "+myOldProbability);
		System.out.println("probability NEW "+probability);
		System.out.println();
		/**
		printRhymeWords();
		printFirstEdges();
		printSecondEdges();
		printThirdEdges();
		printFirstEdgesBack();
		printSecondEdgesBack();
		printThirdEdgesBack();
		System.out.println();*/
	}
	
	/**
	 * 
	 * @returns the rhyme word with the strongest connection (rhyme pair mostly used in shakespeare original)
	 */
	public Word getBestRhymeWord() {
		RhymeEdge best;
		if(rhymeWords.size() == 0) {
			return null;
		} else {
			best = rhymeWords.get(0);
		}
		for(RhymeEdge r : rhymeWords) {
			if(r.getWeight() > best.getWeight()) {
				best = r;
			}
		}
		return best.getTarget();
	}
	
	public void printFirstEdges() {
		System.out.print("FIRST_EDGES_FOR");
		for(FirstForwardEdge f : firstWords) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}
	
	public void printSecondEdges() {
		System.out.print("SECOND_EDGES_FOR");
		for(SecondForwardEdge f : secondWords) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}
	
	public void printThirdEdges() {
		System.out.print("THIRD_EDGES_FOR");
		for(ThirdForwardEdge f : thirdWords) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}
	
	public void printFirstEdgesBack() {
		System.out.print("FIRST_EDGES_BACK");
		for(FirstBackwardEdge f : firstWordsBack) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}
	
	public void printSecondEdgesBack() {
		System.out.print("SECOND_EDGES_BACK");
		for(SecondBackwardEdge f : secondWordsBack) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}
	
	public void printThirdEdgesBack() {
		System.out.print("THIRD_EDGES_BACK");
		for(ThirdBackwardEdge f : thirdWordsBack) {
			System.out.print("--"+f.getWeight()+"-->"+f.getTarget().getWord());
		}
	}

	@Override
	public int compareTo(Word w) {
		if(w.getAmount() > amount) {
			return 1;
		} else if(w.getAmount() < amount){
			return -1;
		}
		return 0;
	}

	public void printRhymeWords() {
		System.out.print("RHYME_WORDS FOR: "+word);
		for(RhymeEdge r : rhymeWords) {
			System.out.print("--"+r.getWeight()+"-->"+r.getTarget().getWord()+" ");
		}
	}

}
