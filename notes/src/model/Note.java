package model;

import java.util.List;

public class Note {
	private int idn;
	private String title;
	private String content;
	
	
	
	public boolean validate(List<String> validationMessages) {
	if (title == null || title.trim().isEmpty() || title.length() < 4) {
		validationMessages.add("The title must be higher than 3 characters.");
	} else if (title.length() > 50) {
		validationMessages.add("The title cannot be higher than 50 characters.");
	}

	
	if (content == null || content.trim().isEmpty() || content.length() < 11) {
		validationMessages.add("The content must be higher than 10 characters.");
	} else if (content.length() > 1000) {
		validationMessages.add("The content cannot be higher than 1000 characters.");
	}

	if (validationMessages.isEmpty())
		return true;
	else
		return false;
	}
	
	public int getIdn() {
		return idn;
	}
	public void setIdn(int idn) {
		this.idn = idn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String newContent) {
		this.content = newContent;
	}
	
	


}
