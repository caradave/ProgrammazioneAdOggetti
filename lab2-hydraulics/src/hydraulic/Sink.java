package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		this.name = name;
	}
	
	//non necessario scrivere @Override, ma per la chiarezza del codice è sempre meglio farlo
	@Override
	public void connect(Element elem) {
	}
	
}
