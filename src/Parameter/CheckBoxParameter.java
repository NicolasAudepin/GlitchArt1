package Parameter;

public class CheckBoxParameter extends ParameterParent{
	
	boolean defaultValue=false;

	/**
	 * 
	 * @param name
	 * @param description
	 * @param placement
	 * @param graphicalPlacement
	 * @param defaultValue
	 */
	public CheckBoxParameter(String name,String description, int placement,int graphicalPlacement, boolean defaultValue) {
		
		super(name, description, placement, graphicalPlacement);
		this.defaultValue=defaultValue;
	}

	public boolean getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	

}
