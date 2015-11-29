public class commonResource {
	public volatile int commonCounter;
		
		public void increment(){
			commonCounter += 1;
		}
		public void decreemnt(){
			commonCounter -=1;
		}
		public int get() {
			return commonCounter;
		}

}
