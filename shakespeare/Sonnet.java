package shakespeare;

import java.util.ArrayList;

/**
 * 
 * @author oleschmitt
 *	
 *	class to represent a sonnet
 */

public class Sonnet {
	
	ArrayList<String> line1 = new ArrayList<String>();
	ArrayList<String> line2 = new ArrayList<String>();
	ArrayList<String> line3 = new ArrayList<String>();
	ArrayList<String> line4 = new ArrayList<String>();
	ArrayList<String> line5 = new ArrayList<String>();
	ArrayList<String> line6 = new ArrayList<String>();
	ArrayList<String> line7 = new ArrayList<String>();
	ArrayList<String> line8 = new ArrayList<String>();
	ArrayList<String> line9 = new ArrayList<String>();
	ArrayList<String> line10 = new ArrayList<String>();
	ArrayList<String> line11 = new ArrayList<String>();
	ArrayList<String> line12 = new ArrayList<String>();
	ArrayList<String> line13 = new ArrayList<String>();
	ArrayList<String> line14 = new ArrayList<String>();
	ArrayList<String> line15 = new ArrayList<String>();
	
	ArrayList<ArrayList<String>> sonnet;
	
	//words in array to work on to create sonnet
	String[] a;
	
	MyReaderWriter myReaderWriter;
	
	//between 1 -> 154 (the originals)
	int myNumber;
	
	public Sonnet(String[] in, MyReaderWriter r, int counter) {
		myNumber = counter;
		myReaderWriter = r;

		//make own array
		a = new String[in.length];
		for(int i=0; i<in.length;i++) {
			a[i] = new String(in[i]);
		}
		
		//fix first element
		if(a[0].contains("\\")) {
			a[0] = a[0].substring(a[0].indexOf('\\')+2, a[0].length());
		}
		
		//fix last element if necessary
		if(a[a.length-1].contains("}")) {
			a[a.length-1] = a[a.length-1].substring(0, a[a.length-1].indexOf('}'));
		}
		
		sonnet = new ArrayList<ArrayList<String>>();
		sonnet.add(line1);
		sonnet.add(line2);
		sonnet.add(line3);
		sonnet.add(line4);
		sonnet.add(line5);
		sonnet.add(line6);
		sonnet.add(line7);
		sonnet.add(line8);
		sonnet.add(line9);
		sonnet.add(line10);
		sonnet.add(line11);
		sonnet.add(line12);
		sonnet.add(line13);
		sonnet.add(line14);
		sonnet.add(line15);
		
		//add words to their lines
		int line=1;
		for(String s:a) {
			boolean incLine = false;
			//word shows line break?
			if(s.contains("\\")) {
				incLine = true;
				s=s.substring(0, s.indexOf('\\'));
			}
			switch(line) {
			case 1:
				
				line1.add(s);
				break;
				
			case 2:
				
				line2.add(s);
				break;
				
			case 3:
				
				line3.add(s);
				break;
				
			case 4:
				
				line4.add(s);
				break;
				
			case 5:
				
				line5.add(s);
				break;
				
			case 6:
				
				line6.add(s);
				break;
				
			case 7:
				
				line7.add(s);
				break;
				
			case 8:
				
				line8.add(s);
				break;
				
			case 9:
				
				line9.add(s);
				break;
				
			case 10:
				
				line10.add(s);
				break;
				
			case 11:
				
				line11.add(s);
				break;
				
			case 12:
				
				line12.add(s);
				break;
				
			case 13:
				
				line13.add(s);
				break;
				
			case 14:
				
				line14.add(s);
				break;
				
			case 15:
				
				line15.add(s);
				break;

			}
			//the next word is in the next line
			if(incLine) {
				line++;
			}
		}
	}
	
	public int getNumber() {
		return myNumber;
	}
	
	/**
	 * 
	 * @param line, which line
	 * @param word, which position in line
	 * @return word as string
	 */
	String get(int line, int word) {
		line--;
		word--;
		if(line < 15 &&word < sonnet.get(line).size()) {
			return sonnet.get(line).get(word);
		}
		return null;
	}
	
	public String[] getA() {
		return a;
	}
	
	public ArrayList<ArrayList<String>> getSonnet() {
		return sonnet;
	}
	
	public void print() {
		int lineCounter = 0;
		int wordCounter = 0;
		for(ArrayList<String> line : sonnet) {
			if(line.size() < 1) {
				break;
			}
			lineCounter++;
			System.out.println("LINE "+lineCounter);
			for(String word : line) {
				wordCounter++;
				System.out.print(wordCounter+": "+word+" ");
			}
			wordCounter=0;
			System.out.println();
		}
	}
	
	/**
	 * print only the words of the sonnet
	 */
	public void printLyrical() {
		for(ArrayList<String> line : sonnet) {
			if(line.size() < 1) {
				break;
			}
			for(String word : line) {
				System.out.print(word+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * initialize the word relations for this sonnet
	 */
	public void initSonnetWords() {
		setRhymeWords();
		setLineWords();
	}
	
	
	/**
	 * put edges to neighbouring words in this line
	 */
	public void setLineWords() {
		for(ArrayList<String> line : sonnet) {
			int wordCounter = 0;
			for(String s : line) {
				Word curWord = myReaderWriter.getWordByString(s);
				//add FirstForwardWord
				if(wordCounter < line.size()-1) {
					curWord.addFirstWord(myReaderWriter.getWordByString(line.get(wordCounter+1)));
				}
				//add SecondForwardWord
				if(wordCounter < line.size()-2) {
					curWord.addSecondWord(myReaderWriter.getWordByString(line.get(wordCounter+2)));
				}
				//add ThirdForwardWord
				if(wordCounter < line.size()-3) {
					curWord.addThirdWord(myReaderWriter.getWordByString(line.get(wordCounter+3)));
				}
				//add FourthForwardWord
				if(wordCounter < line.size()-4) {
					curWord.addFourthWord(myReaderWriter.getWordByString(line.get(wordCounter+4)));
				}
				//add FifthForwardWord
				if(wordCounter < line.size()-5) {
					curWord.addFifthWord(myReaderWriter.getWordByString(line.get(wordCounter+5)));
				}
				
				
				//add FirstBackwardWord
				if(wordCounter > 0) {
					curWord.addFirstWordBack(myReaderWriter.getWordByString(line.get(wordCounter-1)));
				}
				//add SecondBackwardWord
				if(wordCounter > 1) {
					curWord.addSecondWordBack(myReaderWriter.getWordByString(line.get(wordCounter-2)));
				}
				//add ThirdBackwardWord
				if(wordCounter > 2) {
					curWord.addThirdWordBack(myReaderWriter.getWordByString(line.get(wordCounter-3)));
				}
				//add FourthBackwardWord
				if(wordCounter > 3) {
					curWord.addFourthWordBack(myReaderWriter.getWordByString(line.get(wordCounter-4)));
				}
				//add FifthBackwardWord
				if(wordCounter > 4) {
					curWord.addFifthWordBack(myReaderWriter.getWordByString(line.get(wordCounter-5)));
				}
				
					
				wordCounter++;
			}
		}
	}
	
	/**
	 * put rhyme relations. There is one sonnet with 9 lines. and 2 sonnets with 15 lines in the original.
	 */
	public void setRhymeWords() {
		if(line1.size() != 0 && line14.size()  != 0 && line15.size() == 0) {
			setRhymeWordsAs14Liner();
		}
	}
	
	public void setRhymeWordsAs14Liner() {
		
		//1 -> 3 && 3 -> 1
		myReaderWriter.getWordByString(line1.get(line1.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line3.get(line3.size()-1)));
		myReaderWriter.getWordByString(line3.get(line3.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line1.get(line1.size()-1)));
		
		//2 -> 4 && 4 -> 2
		myReaderWriter.getWordByString(line2.get(line2.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line4.get(line4.size()-1)));
		myReaderWriter.getWordByString(line4.get(line4.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line2.get(line2.size()-1)));
		
		//5 -> 7 && 7 -> 5
		myReaderWriter.getWordByString(line5.get(line5.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line7.get(line7.size()-1)));
		myReaderWriter.getWordByString(line7.get(line7.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line5.get(line5.size()-1)));

		//6 -> 8 && 8 -> 6
		myReaderWriter.getWordByString(line6.get(line6.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line8.get(line8.size()-1)));
		myReaderWriter.getWordByString(line8.get(line8.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line6.get(line6.size()-1)));

		//9 -> 11 && 11 -> 9
		myReaderWriter.getWordByString(line9.get(line9.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line11.get(line11.size()-1)));
		myReaderWriter.getWordByString(line11.get(line11.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line9.get(line9.size()-1)));

		//10 -> 12 && 12 -> 10
		myReaderWriter.getWordByString(line10.get(line10.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line12.get(line12.size()-1)));
		myReaderWriter.getWordByString(line12.get(line12.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line10.get(line10.size()-1)));

		//13 -> 14 && 14 -> 13
		myReaderWriter.getWordByString(line13.get(line13.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line14.get(line14.size()-1)));
		myReaderWriter.getWordByString(line14.get(line14.size()-1)).addRhymeWord(myReaderWriter.getWordByString(line13.get(line13.size()-1)));
		
	}

}
