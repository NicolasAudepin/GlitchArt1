package Parameter;

public class FilterParameter {
	
	private String name;
	private int placement;
	int graphicalPlacement;
	int value;
	
	
	public FilterParameter(String name,int placement){
		this.name=name;
		this.placement = placement;
		this.setGraphicalPlacement(placement);					
	}
	
	public FilterParameter(String name,int placement, int graphicalPlacement){
		this.name=name;
		this.placement = placement;
		this.graphicalPlacement= graphicalPlacement;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPlacement() {
		return placement;
	}

	public void setPlacement(int placement) {
		this.placement = placement;
	}

	public int getGraphicalPlacement() {
		return graphicalPlacement;
	}

	public void setGraphicalPlacement(int graphicalPlacement) {
		this.graphicalPlacement = graphicalPlacement;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public void setValue(int value) {
		this.value=value;
	}


}
