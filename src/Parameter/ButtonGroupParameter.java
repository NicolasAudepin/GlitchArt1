package Parameter;

public class ButtonGroupParameter extends ParameterParent{
	
	String valueName;
	int value;

	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param placement
	 * @param valueName
	 * @param gaphicalPlacement
	 */
	public ButtonGroupParameter(String name,String description, int placement,String valueName,int gaphicalPlacement) {
		super(name, description, placement);
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
