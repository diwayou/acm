package com.diwayou.acm.leetcode.lc300;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/design-twitter/
 * <p>
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 * <p>
 * 示例:
 * Twitter twitter = new Twitter();
 * <p>
 * // 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
 * twitter.postTweet(1, 5);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * twitter.getNewsFeed(1);
 * <p>
 * // 用户1关注了用户2.
 * twitter.follow(1, 2);
 * <p>
 * // 用户2发送了一个新推文 (推文id = 6).
 * twitter.postTweet(2, 6);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
 * // 推文id6应当在推文id5之前，因为它是在5之后发送的.
 * twitter.getNewsFeed(1);
 * <p>
 * // 用户1取消关注了用户2.
 * twitter.unfollow(1, 2);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * // 因为用户1已经不再关注用户2.
 * twitter.getNewsFeed(1);
 */
public class Lc355 {

    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        // 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
        twitter.postTweet(1, 5);

        // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
        System.out.println(twitter.getNewsFeed(1));

        // 用户1关注了用户2.
        twitter.follow(1, 2);

        // 用户2发送了一个新推文 (推文id = 6).
        twitter.postTweet(2, 6);

        // 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
        // 推文id6应当在推文id5之前，因为它是在5之后发送的.
        System.out.println(twitter.getNewsFeed(1));

        // 用户1取消关注了用户2.
        twitter.unfollow(1, 2);

        // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
        // 因为用户1已经不再关注用户2.
        System.out.println(twitter.getNewsFeed(1));
    }

    static class Twitter {

        private int time = 0;

        Map<Integer, Tweet> tweetRepo = new HashMap<>();
        Map<Integer, User> userRepo = new HashMap<>();


        private class Tweet {
            int time;
            int tweetId;
            Tweet next;

            public Tweet(int time, int tweetId) {
                this.time = time;
                this.tweetId = tweetId;
            }
        }

        private class User {
            public User(int userId) {
                this.userId = userId;
            }

            int userId;
            Set<Integer> follow = new HashSet<>();

            public void post(Tweet tweet) {
                if (head == null) {
                    head = tweet;
                } else {
                    tweet.next = head;
                    head = tweet;
                }
            }

            Tweet head;
        }


        /**
         * Initialize your data structure here.
         */
        public Twitter() {

        }

        /**
         * Compose a new tweet.
         */
        public void postTweet(int userId, int tweetId) {
            userRepo.putIfAbsent(userId, new User(userId));
            userRepo.get(userId).post(new Tweet(time++, tweetId));
        }

        Comparator<Tweet> cmp;

        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
         */
        public List<Integer> getNewsFeed(int userId) {
            userRepo.putIfAbsent(userId, new User(userId));
            User user = userRepo.get(userId);


            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (b.time - a.time));
            if (user.head != null) {
                pq.add(user.head);
            }
            for (Integer u : user.follow) {
                Tweet head = userRepo.get(u).head;
                if (head != null) {
                    pq.add(head);
                }
            }
            List<Integer> l = new ArrayList<>();
            while (l.size() < 10 && !pq.isEmpty()) {
                Tweet poll = pq.poll();
                l.add(poll.tweetId);
                if (poll.next != null) {
                    pq.add(poll.next);
                }
            }

            return l;
        }

        /**
         * Follower follows a followee. If the operation is invalid, it should be a no-op.
         */
        public void follow(int followerId, int followeeId) {
            if (followerId == followeeId) {
                return;
            }
            userRepo.putIfAbsent(followerId, new User(followerId));
            userRepo.putIfAbsent(followeeId, new User(followeeId));
            userRepo.get(followerId).follow.add(followeeId);
        }

        /**
         * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
         */
        public void unfollow(int followerId, int followeeId) {
            userRepo.putIfAbsent(followerId, new User(followerId));
            Set<Integer> follow = userRepo.get(followerId).follow;
            if (!follow.isEmpty()) {
                follow.remove(followeeId);
            }
        }
    }
}
