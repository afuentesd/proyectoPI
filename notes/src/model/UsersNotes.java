package model;

import java.util.List;

public class UsersNotes {
	private int idu;
	private int idn;
	private int owner; //0=false,1=true
	private int archived;//0=false,1=true
	private int pinned;//0=false,1=true
	
	
	public boolean validate(List<String> validationMessages) {
        if (owner!=0 && owner!=1) {
            validationMessages.add("Fill in the Owner field with a correct value (0=false,1=true).");
        } 

        if (archived!=0 && archived!=1) {
            validationMessages.add("Fill in the Archived field with a correct value (0=false,1=true).");
        } 

        if (pinned!=0 && pinned!=1) {
            validationMessages.add("Fill in the Pinned field with a correct value (0=false,1=true).");
        } 

        if (validationMessages.isEmpty())
            return true;
        else
            return false;
    }
	
	public int getIdu() {
		return idu;
	}
	public void setIdu(int idu) {
		this.idu = idu;
	}
	
	public int getIdn() {
		return idn;
	}
	public void setIdn(int idn) {
		this.idn = idn;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int propietary) {
		this.owner = propietary;
	}
	public int getArchived() {
		return archived;
	}
	public void setArchived(int stored) {
		this.archived = stored;
	}
	public int getPinned() {
		return pinned;
	}
	public void setPinned(int marked) {
		this.pinned = marked;
	}
	
	
	


}
