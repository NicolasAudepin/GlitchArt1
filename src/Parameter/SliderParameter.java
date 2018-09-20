package Parameter;

public class SliderParameter extends ParameterParent{
	private int min;
	private int max;
	private int defaultValue;
	private int value;

	/**
	 * 
	 * @param name
	 * @param placement
	 * @param graphicalPlacement
	 * @param min
	 * @param max
	 * @param defaultValue
	 * @param description
	 */
	public SliderParameter(String name,int placement ,int graphicalPlacement,int min, int max, int defaultValue,String description) {
		
		super(name,description,placement,graphicalPlacement);
		this.min=min;
		this.max=max;
		this.defaultValue=defaultValue;
		// TODO Auto-generated constructor stub
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

}
