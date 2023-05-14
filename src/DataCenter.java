import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataCenter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8027627375390211793L;
	private ArrayList<User> users;
	private int currUser;
	
	public DataCenter() {
	File dataCenter = new File("src\\dataCenter.dat");
	if (dataCenter.length()==0) {
		users = new ArrayList<User>();
		this.save();
	}
	
	try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataCenter))) {
			DataCenter dc = (DataCenter)(ois.readObject());
			users = dc.users;
		}
	catch (IOException | ClassNotFoundException ie) {
			ie.printStackTrace();
	} 
	}
	
	public void addUser(User user) {
		users.add(user);
		this.save();
	}
	
	public void save() {
		File dataCenter = new File("src\\dataCenter.dat");
		try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataCenter))) {
			oos.writeObject(this);
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	public int getCurrUser() {
		return currUser;
	}
	
	public boolean checkUsers(String username) {
		for (int i=0;i<this.getUsers().size();++i) {
			if (username.equals(this.getUsers().get(i).getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	public int checkBooks(String ISBN, String path) {
		for (int i=0;i<this.getUsers().get(this.currUser).getBooks().size();++i) {
			if (ISBN.equals(this.getUsers().get(this.currUser).getBooks().get(i).getISBN()) || path.equals(this.getUsers().get(this.currUser).getBooks().get(i).getPath())) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean validateLogin(String username, String password) {
		for (int i=0;i<this.getUsers().size();++i) {
			if (username.equals(this.getUsers().get(i).getUsername()) && password.equals(this.getUsers().get(i).getPassword())) {
				currUser = i;
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Book> searchBooks(String term, int type) {
		ArrayList<Book> tmp = new ArrayList<Book>();
		switch (type) {
		case 0:
			for (int i=0;i<this.users.get(currUser).getBooks().size();++i) {
				if (this.users.get(currUser).getBooks().get(i).getTitle().equalsIgnoreCase(term)) {
					tmp.add(this.users.get(currUser).getBooks().get(i));
				}
			}
			break;
		case 1:
			for (int i=0;i<this.users.get(currUser).getBooks().size();++i) {
				if (this.users.get(currUser).getBooks().get(i).getAuthor().equals(term)) {
					tmp.add(this.users.get(currUser).getBooks().get(i));
				}
			}
			break;
		case 2:
			for (int i=0;i<this.users.get(currUser).getBooks().size();++i) {
				if (this.users.get(currUser).getBooks().get(i).getISBN().equals(term)) {
					tmp.add(this.users.get(currUser).getBooks().get(i));
				}
			}
			break;
		default:
			break;
		}
		return tmp;
	}
	
	public ArrayList<StringBuilder> uniqueWords(String txt, Words unqWrds) {
		String[] tmp = txt.split(" ");
	    StringBuilder[] swrds = new StringBuilder[tmp.length];
	    Arrays.setAll(swrds, i -> new StringBuilder(tmp[i]));
	    ArrayList<StringBuilder> wrds = new ArrayList<>(Arrays.asList(swrds));
		unqWrds.addWord(wrds.get(0));
		for (int i=1;i<wrds.size();++i) {
			if (unqWrds.checkWord(wrds.get(i))) {
				
			}
			else {
				unqWrds.addWord(wrds.get(i));
			}
		}
		return unqWrds.getWords();
	}
	
	public String[][] topTenWords(String txt, Words unqWrds) {
		String[][] tpTnWrd = new String[10][2];
		String[] tmp = txt.split(" ");
	    StringBuilder[] wrds = new StringBuilder[tmp.length];
	    Arrays.setAll(wrds, i -> new StringBuilder(tmp[i]));
	    
	    ArrayList<StringBuilder> wrdCnt = new ArrayList<StringBuilder>();
	    for (int i=0;i<unqWrds.getWords().size();++i) {
	    	int cntTmp = 0;
	    	for (int j=0;j<wrds.length;++j) {
	    		if (unqWrds.getWords().get(i).toString().equalsIgnoreCase(wrds[j].toString())) {
	    			++cntTmp;
	    		}
	    	}
	    	StringBuilder sbTmp = new StringBuilder();
	    	sbTmp.append(cntTmp);
	    	wrdCnt.add(sbTmp);
	    }
	    int max = -1;
    	int idx = 0;
	    for (int j=0;j<10;++j) {
	    	max = -1;
	    	idx = 0;
	    	for (int i=0;i<unqWrds.getWords().size();i++) {
	    		if (Integer.parseInt(wrdCnt.get(i).toString())>max) {
	    			max = Integer.parseInt(wrdCnt.get(i).toString());
	    			idx = i;
	    		}
	    	}
	    	tpTnWrd[j][0] = unqWrds.getWords().get(idx).toString();
	    	tpTnWrd[j][1] = wrdCnt.get(idx).toString();
	    	unqWrds.getWords().remove(idx);
	    	wrdCnt.remove(idx);
	    }
	    return tpTnWrd;
	}
	
}
