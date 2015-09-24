package model;

public class CommunityItems {
	private String name,created;
	private int image;

	public CommunityItems(String name, int image, String created) {
		super();
		this.name = name;
		this.image = image;
		this.created = created;
	}

	public CommunityItems() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	
	
	

}
