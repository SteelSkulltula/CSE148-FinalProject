import java.io.Serializable;

public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5422904308252715993L;
	private String title;
	private String author;
	private String ISBN;
	private String path;
	
	public Book(String title, String author, String ISBN, String path) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String toString() {
		return title + " by " + author + ", ISBN: " + ISBN;
	}
	
}
