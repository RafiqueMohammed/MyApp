package arr.myapp;

public class ListViewCollection {
	
	public static class AddNews{
		String title;
		String description;
		String posted_on;
		int _id;
	
		public AddNews(int _id,String title, String description, String posted_on) {
			super();
			this.title = title;
			this.description = description;
			this.posted_on = posted_on;
			this._id = _id;
			
		}
		public String getTitle() {
			return title;
		}
		public String getDescription() {
			return description;
		}
		public String getPosted_on() {
			return posted_on;
		}
		public int get_id() {
			return _id;
		}
				
		
		
	}
}
