import java.util.ArrayList;

public class Words {
	private ArrayList<StringBuilder> words;
	
	public Words() {
		words = new ArrayList<StringBuilder>();
	}
	
	public void addWord(StringBuilder word) {
		words.add(word);
	}
	
	public boolean checkWord(StringBuilder word) {
		for(int i=0; i<words.size(); i++) {
			if(words.get(i).toString().equalsIgnoreCase(word.toString()))
				return true;
		}
		return false;
	}
	
	public String getWord(int idx) {
		return words.get(idx).toString();
	}

	public ArrayList<StringBuilder> getWords() {
		return words;
	}

	public void setWords(ArrayList<StringBuilder> words) {
		this.words = words;
	}
	
	
}
