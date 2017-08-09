package shakespeare;


/**
 * 
 * @author oleschmitt
 * class Edge to make a connection between two words
 *
 */
public class Edge {
	
	Word source;
	Word target;
	double weight = 1;
	double weightInc = 1;
	
	public Edge(Word s, Word t, int wordAmF) {
		source = s;
		target = t;
		weightInc = 1/s.getAmount() + 1/t.getAmount();
		weight = weightInc;

		/**
		//SET EDGE WEIGHT AND WEIGHT INC
		//get the amount in percentage to total all amounts of all words
		double amountInPerc1 = s.getAmountInPerc();
		double amountInPerc2 = t.getAmountInPerc();
		
		//set the param word amount factor to percentage
		double wordAmFInPerc = (double)wordAmF/100;
		
		//the higher the word amount factor in percentage, the lower the weight inc. Done for source and target word. +1 as a constant to avoid 0. 
		weightInc = (amountInPerc1 - amountInPerc1 * wordAmFInPerc)+(amountInPerc2 - amountInPerc2 * wordAmFInPerc)+1;
		*/
		
		/**
		//// SET EDGE WEIGHT AND WEIGHT INC
		//avoid division by zero
		if(wordAmF == 0) {
			wordAmF = 1;
		}
		//make denominators
		int denom1 = (int) (s.getAmount()*(double)(wordAmF/100));
		int denom2 = (int) (t.getAmount()*(double)(wordAmF/100));
		//set weightInc
		weightInc = (1/denom1) + (1/denom2);
		//set weight
		weight = weightInc;
		*/
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void incWeight() {
		weight += weightInc;
	}
	
	public Word getTarget() {
		return target;
	}
	
	public Word getSource() {
		return source;
	}

}
