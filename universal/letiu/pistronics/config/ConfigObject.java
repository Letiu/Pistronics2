package letiu.pistronics.config;

public class ConfigObject {

	public Object value;
	public String comment;
	
	public ConfigObject(Object value, String comment) {
		this.comment = comment;
		this.value = value;
	}
}
