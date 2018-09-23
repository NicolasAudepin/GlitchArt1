package Parameter;

/**
 * Ce paramètre représente un boutton d'un Button groupe
 * @author AUDEPIN
 *
 */
public class ButtonGroupParameter extends ParameterParent{
	
	String valueName;
	int value; // 1 ou 0 

	
	/**
	 * Ce paramètre représente un seul boutton d'un ButtonGroup 
	 * @param name Le nom de son ButtonGroup
	 * @param description
	 * @param placement sera égal au placement du buttonGroup + sa position dans celui-ci
	 * @param valueName le nom de ce boutton
	 * @param gaphicalPlacement le même dans tou le grupe
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
