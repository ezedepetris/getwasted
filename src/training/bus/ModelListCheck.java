package training.bus;

public class ModelListCheck {

	private String name;
	private boolean selected;
	private MySchedule mySchedule;

	public ModelListCheck(MySchedule mySchedule) {
		this.mySchedule = mySchedule; 
		selected = false;
	}

	public MySchedule getMySchedule() {
		return mySchedule;
	}

	public String getName() {
		name = mySchedule.getTime()+ " " + mySchedule.getDay();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
}
