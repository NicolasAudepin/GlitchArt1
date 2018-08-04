package Parameter;

public class ButtonGroupParameter extends FilterParameter{
	
	String valueName;
	int value;

	

	public ButtonGroupParameter(String name, int placement,String valueName,int gaphicalPlacement) {
		super(name, placement);
		this.valueName=valueName;
		this.graphicalPlacement=gaphicalPlacement;
		
	}



	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	
	
}
