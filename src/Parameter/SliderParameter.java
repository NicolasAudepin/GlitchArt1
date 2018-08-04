package Parameter;

public class SliderParameter extends FilterParameter{
	private int min;
	private int max;
	private int defaultValue;
	private int value;

	public SliderParameter(String name,int placement ,int graphicalPlacement,int min, int max, int defaultValue) {
		
		super(name,placement,graphicalPlacement);
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
