package ch.bfh.bti7081.s2018.white.pms.ui.common;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;

public class CustomButton extends Button {
	public enum TypeEnum {NEW_COMMENT,NEW_GOAL,NEW_DIARY_ENTRY,EDIT,DELETE,SAVE,LOGIN};
	private TypeEnum typeString;

    public CustomButton(TypeEnum typeString) {
    	this.typeString = typeString;
    	this.setStyleName("borderless");
    	
    	switch(typeString) {
    		case NEW_DIARY_ENTRY:		
    	        this.setIcon(new ThemeResource("images/plus.png"));
    	        this.setDescription(MessageHandler.NEW_DIARY_ENTRY);
    			break;
    		case NEW_COMMENT:		
    	        this.setIcon(new ThemeResource("images/plus.png"));
    	        this.setDescription(MessageHandler.NEW_COMMENT);
    			break;
    		case NEW_GOAL:
    	        this.setIcon(new ThemeResource("images/plus.png"));
    	        this.setDescription(MessageHandler.ADD_GOAL);
    			break;
    		case EDIT:
    	        this.setIcon(new ThemeResource("images/edit.png"));
    	        this.setDescription(MessageHandler.EDIT);
    			break;
    		case DELETE:
    			this.setIcon(new ThemeResource("images/delete.png"));
    	        this.setDescription(MessageHandler.DELETE);
    			break;
    		case SAVE:
    			this.setIcon(new ThemeResource("images/save.png"));
    	        this.setDescription(MessageHandler.SAVE);
    			break;	
    		case LOGIN:
    			this.setIcon(new ThemeResource("images/login.png"));
    	        this.setDescription(MessageHandler.LOGIN);
    			break;
    	}
        
    }

}
