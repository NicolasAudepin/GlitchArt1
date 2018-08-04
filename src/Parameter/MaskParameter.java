package Parameter;

public class MaskParameter extends FilterParameter{
	
	
	int defaultValue = 0;
	public MaskParameter(String name, int placement,int graphicalPlacement, int defaultValue) {
		
		super(name, placement, graphicalPlacement);
		this.defaultValue = defaultValue;
	}
	
	
	
	
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

}
