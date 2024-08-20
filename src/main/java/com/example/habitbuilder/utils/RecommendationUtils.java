package com.example.habitbuilder.utils;

import com.example.habitbuilder.domain.bo.UserItemInteraction;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationUtils {

    private final List<UserItemInteraction> interactions;

    public RecommendationUtils(List<UserItemInteraction> interactions) {
        this.interactions = interactions;
    }

    public List<String> recommendItemsForUser(Long userId) {
        // 获取当前用户的交互数据
        List<UserItemInteraction> userInteractions = interactions.stream()
                .filter(i -> i.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (userInteractions.isEmpty()) {
            return Collections.emptyList();
        }

        // 获取所有用户的交互数据
        Map<Long, Map<Long, Integer>> userItemRatings = new HashMap<>();

        for (UserItemInteraction interaction : interactions) {
            Long currentUserId = interaction.getUserId();
            Long ItemId = interaction.getItemId();
            int rating = interaction.getRating();

            userItemRatings
                .computeIfAbsent(currentUserId, k -> new HashMap<>())
                .put(ItemId, rating);
        }

        // 创建用户相似度矩阵
        Map<Long, Map<Long, Double>> userSimilarityMatrix = new HashMap<>();

        for (Map.Entry<Long, Map<Long, Integer>> entry1 : userItemRatings.entrySet()) {
            Long userId1 = entry1.getKey();
            Map<Long, Integer> user1Ratings = entry1.getValue();

            for (Map.Entry<Long, Map<Long, Integer>> entry2 : userItemRatings.entrySet()) {
                Long userId2 = entry2.getKey();
                if (userId1.equals(userId2)) continue;

                Map<Long, Integer> user2Ratings = entry2.getValue();

                double similarity = calculatePearsonSimilarity(user1Ratings, user2Ratings);
                userSimilarityMatrix
                    .computeIfAbsent(userId1, k -> new HashMap<>())
                    .put(userId2, similarity);
            }
        }

        // 为用户推荐帖子
        Map<Long, Double> recommendedItemScores = new HashMap<>();
        Map<Long, Integer> userRatings = userItemRatings.get(userId);

        for (Map.Entry<Long, Map<Long, Double>> entry : userSimilarityMatrix.entrySet()) {
            Long otherUserId = entry.getKey();
            Map<Long, Double> similarities = entry.getValue();
            for (Map.Entry<Long, Double> similarityEntry : similarities.entrySet()) {
                Long similarUserId = similarityEntry.getKey();
                Double similarity = similarityEntry.getValue();

                if (userId.equals(similarUserId)) continue;

                Map<Long, Integer> similarUserRatings = userItemRatings.get(similarUserId);

                for (Map.Entry<Long, Integer> ratingEntry : similarUserRatings.entrySet()) {
                    Long ItemId = ratingEntry.getKey();
                    Integer rating = ratingEntry.getValue();

                    if (!userRatings.containsKey(ItemId)) {
                        double currentScore = recommendedItemScores.getOrDefault(ItemId, 0.0);
                        recommendedItemScores.put(ItemId, currentScore + similarity * rating);
                    }
                }
            }
        }

        // 按照得分排序推荐帖子
        return recommendedItemScores.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .map(entry -> "Item ID: " + entry.getKey() + ", Score: " + entry.getValue())
                .collect(Collectors.toList());
    }

    private double calculatePearsonSimilarity(Map<Long, Integer> ratings1, Map<Long, Integer> ratings2) {
        Set<Long> commonItems = new HashSet<>(ratings1.keySet());
        commonItems.retainAll(ratings2.keySet());

        if (commonItems.isEmpty()) return 0;

        double sum1 = 0, sum2 = 0, sum1Sq = 0, sum2Sq = 0, pSum = 0;

        for (Long ItemId : commonItems) {
            int rating1 = ratings1.get(ItemId);
            int rating2 = ratings2.get(ItemId);

            sum1 += rating1;
            sum2 += rating2;
            sum1Sq += Math.pow(rating1, 2);
            sum2Sq += Math.pow(rating2, 2);
            pSum += rating1 * rating2;
        }

        int n = commonItems.size();
        double numerator = pSum - (sum1 * sum2 / n);
        double denominator = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / n) * (sum2Sq - Math.pow(sum2, 2) / n));

        if (denominator == 0) return 0;

        return numerator / denominator;
    }
}
