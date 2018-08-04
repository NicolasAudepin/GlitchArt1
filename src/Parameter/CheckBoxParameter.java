package Parameter;

public class CheckBoxParameter extends FilterParameter{
	
	boolean defaultValue=false;

	public CheckBoxParameter(String name, int placement,int graphicalPlacement, boolean defaultValue) {
		
		super(name, placement, graphicalPlacement);
		this.defaultValue=defaultValue;
	}

	public boolean getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	

}
