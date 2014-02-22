package arr.myapp;

public class ListViewCollection {

	public static class AddNews {
		String title;
		String description;
		String posted_on;
		int _id, status, cloud_id, fav;

		public AddNews(int cloud_id, int _id, String title, String description,
				String posted_on, int flag, int fav) {
			super();
			this.title = title;
			this.description = description;
			this.posted_on = posted_on;
			this._id = _id;
			this.status = flag;
			this.cloud_id = cloud_id;
			this.fav = fav;

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

		public int getStatus() {
			return status;
		}

		public int get_id() {
			return _id;
		}

		public int getCloud_id() {
			return cloud_id;
		}

		public int getFav() {
			return fav;
		}

		public void setStatus(int val) {
			this.status = val;
		}

		public void setFav(int val) {
			this.fav = val;
		}

	}
}
