package Parameter;

public class MaskParameter extends ParameterParent{
	
	
	int defaultValue = 0;
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param placement
	 * @param graphicalPlacement
	 * @param defaultValue
	 */
	public MaskParameter(String name,String description, int placement,int graphicalPlacement, int defaultValue) {
		
		super(name, description,placement ,graphicalPlacement);
		this.defaultValue = defaultValue;
	}
	
	
	
	
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

}
