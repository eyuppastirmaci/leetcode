/**
 * fan-out on read: - each user stores only their own latest FEED_SIZE tweets
 *                  - getNewsFeed k-way merges followees tweets at read time
 *                  - chosen over fan-out on write because follow,unfollow stay O(1) with no per-follower copies; push would win only if reads vastly outnumber writes and follow graphs were stable.
 *
 * not thread-safe: single-threaded by design (per-user locking or synchronized methods would be needed for concurrent use).
 
 * Time: postTweet, follow, unfollow O(1)
 *       getNewsFeed O(F log F), F = followees + self
 * Space: O(E + U * FEED_SIZE), E = follow edges, U = users with tweets
 */
class Twitter {

    private static final int FEED_SIZE = 10;
    private static final Comparator<Tweet> NEWEST_FIRST = Comparator.comparingInt(Tweet::timestamp)
                                                                    .reversed();

    private final Map<Integer, Set<Integer>> following;
    private final Map<Integer, Deque<Tweet>> tweetsByUser;
    private int tweetCounter;

    public Twitter() {
        this.following = new HashMap<>();
        this.tweetsByUser = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweetCounter++;

        Deque<Tweet> tweets = tweetsByUser.computeIfAbsent(userId, k -> new ArrayDeque<>());
        tweets.addFirst(new Tweet(tweetId, tweetCounter));

        if (tweets.size() > FEED_SIZE) {
            tweets.removeLast();
        }
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<FeedEntry> heap = new PriorityQueue<>(
            Comparator.comparing(FeedEntry::tweet, NEWEST_FIRST)
        );

        offerLatest(heap, userId);

        // seed heap with each followee's latest tweet
        Set<Integer> followees = following.get(userId);
        if (followees != null) {
            for (int followeeId : followees) {
                offerLatest(heap, followeeId);
            }
        }

        return drainNewest(heap);
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }

        following.computeIfAbsent(followerId, k -> new HashSet<>())
                 .add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followees = following.get(followerId);

        if (followees != null) {
            followees.remove(followeeId);
        }
    }

    private void offerLatest(PriorityQueue<FeedEntry> heap, int userId) {
        Deque<Tweet> tweets = tweetsByUser.get(userId);

        if (tweets == null) {
            return;
        }

        Iterator<Tweet> it = tweets.iterator();
        heap.offer(new FeedEntry(it.next(), it));
    }

    private List<Integer> drainNewest(PriorityQueue<FeedEntry> heap) {
        List<Integer> feed = new ArrayList<>();

        while (!heap.isEmpty() && feed.size() < FEED_SIZE) {
            FeedEntry entry = heap.poll();

            feed.add(entry.tweet().id());

            Iterator<Tweet> rest = entry.rest();
            if (rest.hasNext()) {
                heap.offer(new FeedEntry(rest.next(), rest));
            }
        }

        return feed;
    }

    private record Tweet(int id, int timestamp) {}

    private record FeedEntry(Tweet tweet, Iterator<Tweet> rest) {}
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
