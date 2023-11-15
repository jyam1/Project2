interface ObserverUser {
    public void setVisible();

    public void addTweet(String message);

    public double returnPositiveMessages();

    public boolean isFollower(String name);

    public int returnTotalMessages();
}
