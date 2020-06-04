package practice.producerconsumer.customblocking;

public class Product {
	
	private int id;
	private String producer;
	
	public Product(int id, String producer) {
		super();
		this.setId(id);
		this.setProducer(producer);
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
